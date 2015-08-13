#version 150

in vec3 vIn;
in vec2 angleData;
in vec3 radiusIn;
in float seed;

layout(std140) uniform GlobalValues {
	float delta;
	float globalTime;
};

uniform mat4 projectionMatrix;

out vec3 vOut;
out vec2 angleOut;
out vec3 radiusOut;
out float seedOut;

out vec3 fragInput;

void main(){
	gl_PointSize = seed;

	float posx = vIn.x;
	float posy = vIn.y;
	float posz = vIn.z;
	float angle1 = angleData.x;
	float angle2 = angleData.y;
	float radiusx = radiusIn.x;
	float radiusy = radiusIn.y;
	float radiusz = radiusIn.z;
	
	float newx = radiusx * sin(angle1) * cos(angle2);
	float newy = radiusy * sin(angle1) * sin(angle2);
	float newz = radiusz * cos(angle1);
	
	if(seed == 0.0) angle1 += delta * 1;
	else if(seed == 1.0) angle2 += delta * 1;
	
	vOut = vec3(newx, newy, newz);
	angleOut = vec2(angle1, angle2);
	radiusOut = radiusIn;
	seedOut = seed;
	
	fragInput = radiusIn;
	
	gl_Position = projectionMatrix * vec4(posx, posy, 0, 1);
}