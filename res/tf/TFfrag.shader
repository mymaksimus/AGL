#version 150

in vec3 fragInput;

out vec4 outColor;

layout(std140) uniform GlobalValues {
	float delta;
	float globalTime;
};

void main(){
	outColor = vec4((fragInput.x - 0.5) * 2.0, (fragInput.y - 0.5) * 2.0, (fragInput.z - 0.5) * 2.0, 0.5);
}