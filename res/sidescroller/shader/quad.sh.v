#version 150

in vec3 vertexIn;
in vec2 texCoords;
in vec3 normalIn;

uniform mat4 camera;
uniform mat4 model;

out vec3 fVertex;
out vec3 fNormal;
out vec2 ftexCoords;

void main(){
	fNormal = normalIn;
	fVertex = vertexIn;
	ftexCoords = texCoords;
	gl_Position = camera * model * vec4(vertexIn, 1);
}