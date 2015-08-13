#version 150

in vec3 fVertex;
in vec3 fNormal;

struct Light {
	vec4 position;
	vec3 color;
	vec3 ambient;
	float attenuation;
};

const int MAX_LIGHTS = 100;

uniform mat4 model;
uniform int lightCount;
uniform Light lights[MAX_LIGHTS];

vec3 calculateLight(Light light, vec3 fragmentColor, vec3 normal, vec3 fragmentPosition){
	vec3 rayDirection;
	float finalAttenuation;	
	if(light.position.w == 0.0){ //directional light
		rayDirection = normalize(light.position.xyz);
		finalAttenuation = 1.0;
	}
	else { //point light
		rayDirection = normalize(light.position.xyz - fragmentPosition);
		
		//calculate attenuation
		float distanceToLight = length(light.position.xyz - fragmentPosition);
		finalAttenuation = 1.0 / (1.0 + light.attenuation * pow(distanceToLight, 2));
	}
	
	//calculate ambient
	vec3 finalAmbient = light.ambient * light.color * fragmentColor;
	
	//diffuse
	float diffuseIntense = max(0, dot(normal, rayDirection));	
	vec3 finalDiffuse = diffuseIntense * fragmentColor * light.color;
	
	//calculate final color
	return finalAmbient + finalAttenuation * finalDiffuse;
}

vec4 getLightColor(vec4 texColor){
	vec3 colorSum = vec3(0);
	vec3 normal = normalize(transpose(inverse(mat3(model))) * fNormal);
	vec3 fragmentPosition = vec3(model * vec4(fVertex, 1));
	for(int i = 0; i < lightCount; i++){
		colorSum += calculateLight(lights[i], texColor.rgb, normal, fragmentPosition);
	}
	return vec4(colorSum, texColor.a);	
}