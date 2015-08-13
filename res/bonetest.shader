#version 150

in vec2 vertexIn;
in vec3 vertexColor;

uniform mat4 camera;
uniform mat4 model;

out vec3 fragmentColor;

void main(){
	fragmentColor = vertexColor;
	gl_Position = camera * model * vec4(vertexIn, 0, 1);
}

###

#version 150

in vec3 fragmentColor;

out vec4 outColor;

void main(){
	outColor = vec4(fragmentColor, 1);
}