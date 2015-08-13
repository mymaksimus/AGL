#version 150

in vec2 ftexCoords;

uniform sampler2D texture;

out vec4 outColor;

//prototypes: light shader
vec4 getLightColor(vec4);

void main(){
	outColor = getLightColor(texture2D(texture, ftexCoords));
}