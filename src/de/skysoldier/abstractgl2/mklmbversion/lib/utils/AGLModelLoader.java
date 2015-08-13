package de.skysoldier.abstractgl2.mklmbversion.lib.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import de.skysoldier.abstractgl2.mklmbversion.lib.base.AGLResource;

public class AGLModelLoader {

	public static float[] loadObj(AGLResource resource, boolean calculateNormals){
		return loadObj(resource, new Vector3f(1.0f, 1.0f, 1.0f), calculateNormals);
	}
	
	public static float[] loadObj(AGLResource resource, Vector3f scale, boolean calculateNormals){
		ArrayList<ArrayList<Vector3f>> faces = new ArrayList<>();
		ArrayList<ArrayList<Vector2f>> facesUv = new ArrayList<>();
		
		ArrayList<Vector3f> verticies = new ArrayList<>();
		ArrayList<Vector2f> uv = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
		String line;
		try{
			while((line = reader.readLine()) != null){
				if(!line.isEmpty()){
					String lineData[] = line.split(" ");
					String dataType = lineData[0];
					if(dataType.equals("f")){
						String section1[] = lineData[1].split("/");
						String section2[] = lineData[2].split("/");
						String section3[] = lineData[3].split("/");
						ArrayList<Vector3f> face = new ArrayList<>();
						face.add(verticies.get(Integer.valueOf(section1[0]) - 1));
						face.add(verticies.get(Integer.valueOf(section2[0]) - 1));
						face.add(verticies.get(Integer.valueOf(section3[0]) - 1));
						faces.add(face);
						ArrayList<Vector2f> faceUv = new ArrayList<>();
						faceUv.add(uv.get(Integer.valueOf(section1[1]) - 1));
						faceUv.add(uv.get(Integer.valueOf(section2[1]) - 1));
						faceUv.add(uv.get(Integer.valueOf(section3[1]) - 1));
						facesUv.add(faceUv);
					}
					else if(dataType.equals("v")){
						float x = Float.valueOf(lineData[1]);
						float y = Float.valueOf(lineData[2]);
						float z = Float.valueOf(lineData[3]);
						verticies.add(new Vector3f(x, y, z));
					}
					else if(dataType.equals("vt")){
						float u = Float.parseFloat(lineData[1]);
						float v = Float.parseFloat(lineData[2]);
//						System.out.println(uv.size() + 1 + ": " + u + ", " + v);
						uv.add(new Vector2f(u, v));
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return createVbo(faces, facesUv, calculateNormals);
	}
	
	private static float[] createVbo(ArrayList<ArrayList<Vector3f>> faces, ArrayList<ArrayList<Vector2f>> facesUv, boolean calculateNormals){
		float vbo[] = new float[faces.size() * 24]; //v3f: pos, normal v2f: uv <- per face [triangles]
		int vboIndex = 0;
		for(int i = 0; i < faces.size(); i++){
			ArrayList<Vector3f> face = faces.get(i);
			ArrayList<Vector2f> faceUv = facesUv.get(i);
			Vector3f normal = null;
			if(calculateNormals){
				Vector3f v1 = face.get(0);
				Vector3f v2 = face.get(1);
				Vector3f v3 = face.get(2);
				Vector3f U = Vector3f.sub(v2, v1, new Vector3f());
				Vector3f V = Vector3f.sub(v3, v1, new Vector3f());
				float nx = U.y * V.z - U.z * V.y;
				float ny = U.z * V.x - U.x * V.z;
				float nz = U.x * V.y - U.y * V.x;
				normal = (Vector3f) new Vector3f(nx, ny, nz).normalise();
				//Nx = UyVz - UzVy
				//Ny = UzVx - UxVz
				//Nz = UxVy - UyVx
			}
			for(int j = 0; j < face.size(); j++){
				Vector3f vertex = face.get(j);
				Vector2f uv = faceUv.get(j);
				vbo[vboIndex++] = vertex.x;
				vbo[vboIndex++] = vertex.y;
				vbo[vboIndex++] = vertex.z;
				if(calculateNormals){
					vbo[vboIndex++] = normal.x;
					vbo[vboIndex++] = normal.y;
					vbo[vboIndex++] = normal.z;
				}
				vbo[vboIndex++] = uv.x;
				vbo[vboIndex++] = 1 - uv.y;
//				System.out.println("v: " + vbo[vboIndex - 8] + ", " + vbo[vboIndex - 7] + ", " + vbo[vboIndex - 6] + "  normal: " + vbo[vboIndex - 5] + ", " + vbo[vboIndex - 4] + ", " + vbo[vboIndex - 3] + "  vt: " + vbo[vboIndex - 2] + ", " + vbo[vboIndex - 1]);
			}
		}
		return vbo;
	}
}