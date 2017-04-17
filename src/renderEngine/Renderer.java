package renderEngine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import models.RawModel;
import models.TexturedModel;

public class Renderer {
	public void prepare() {
		glClear(GL_COLOR_BUFFER_BIT);
		glClearColor(1, 0, 0, 1);
	}
	
	public void render(TexturedModel texturedModel) {
		RawModel model = texturedModel.getModel();
		
		glBindVertexArray(model.getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texturedModel.getTexture().getTextureID());
		glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
		glBindTexture(GL_TEXTURE_2D, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glBindVertexArray(0);
	}
}
