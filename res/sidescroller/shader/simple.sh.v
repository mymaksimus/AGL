#version 150

in vec3 vertexIn;

uniform mat4 camera;
uniform mat4 model;

void main(){
	gl_Position = camera * model * vec4(vertexIn, 1);
}