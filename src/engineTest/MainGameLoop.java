package engineTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import shaders.StaticShader;
import terrain.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class MainGameLoop {
	public static void main(String args[]) {
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		
		Light light = new Light(new Vector3f(20000, 40000, 20000), new Vector3f(1, 1, 1));
		
		TexturedModel treeModel = new TexturedModel(OBJLoader.loadObjModel("model_tree", loader), new ModelTexture(loader.loadTexture("tex_tree")));
		TexturedModel grassModel = new TexturedModel(OBJLoader.loadObjModel("model_grass", loader), new ModelTexture(loader.loadTexture("tex_grass")));
		TexturedModel fernModel = new TexturedModel(OBJLoader.loadObjModel("model_fern", loader), new ModelTexture(loader.loadTexture("tex_fern")));
		
		grassModel.getTexture().setHasTransparency(true);
		grassModel.getTexture().setUseFakeLighting(true);
		fernModel.getTexture().setHasTransparency(true);
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		
		for(int i = 0; i < 500; i++) {
			entities.add(new Entity(treeModel, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), 0, 0, 0, 3));
			entities.add(new Entity(grassModel, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), 0, 0, 0, 1));
			entities.add(new Entity(fernModel, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), 0, 0, 0, 0.6f));
		}
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("ttex_grass"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("ttex_mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("ttex_grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("ttex_path"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("bm_blendMap1"));
		
		Terrain terrain1 = new Terrain(0, -1, loader, texturePack, blendMap);
		Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap);
		
		Camera camera = new Camera();
		MasterRenderer renderer = new MasterRenderer();
		
		TexturedModel playerModel = new TexturedModel(OBJLoader.loadObjModel("model_player", loader), new ModelTexture(loader.loadTexture("tex_player")));
		Player player = new Player(playerModel, new Vector3f(100, 0, -50), 0, 0, 0, 1);
		
		while(!Display.isCloseRequested()) {
			camera.move();
			player.move();
			
			renderer.processTerrain(terrain1);
			renderer.processTerrain(terrain2);
			
			for(Entity entity : entities) {
				renderer.processEntity(entity);
			}
			
			renderer.processEntity(player);
			
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}
