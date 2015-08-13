#version 150

in vec3 vertexIn;
in vec2 texCoords;

uniform mat4 camera;
uniform mat4 model;

out vec2 ftexCoords;

void main(){
	ftexCoords = texCoords;
	gl_Position = camera * model * vec4(vertexIn, 1);
}

###

#version 150

in vec2 ftexCoords;

uniform sampler2D texture;

out vec4 outColor;

void main(){
	outColor = texture2D(texture, ftexCoords);
}