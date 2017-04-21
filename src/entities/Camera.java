package entities;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	private float distanceFromPlayer = 50;
	private float angleAroundPlayer = 0;

	private Vector3f position = new Vector3f(100, 35, 50);
	private float pitch = 20;
	private float yaw = 0;
	private float roll;

	private Player player;

	public Camera(Player player) {
		this.player = player;
	}
	
	public void move() {
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		
		calculateCameraPosition(horizontalDistance, verticalDistance);
		
		this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	private void calculateCameraPosition(float horizontalDistance, float verticalDistance) {
		position.y = player.getPosition().y + verticalDistance + 10;
		
		float theta = player.getRotY() + angleAroundPlayer;
		float offsetX = horizontalDistance * (float)Math.sin(Math.toRadians(theta));
		float offsetZ = horizontalDistance * (float)Math.cos(Math.toRadians(theta));
		
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
	}
	
	private float calculateHorizontalDistance() {
		return distanceFromPlayer * (float)Math.cos(Math.toRadians(pitch));
	}
	
	private float calculateVerticalDistance() {
		return distanceFromPlayer * (float)Math.sin(Math.toRadians(pitch));
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}

	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() * 0.05f;
		distanceFromPlayer -= zoomLevel;
		
		if(distanceFromPlayer < 26.0) {
			distanceFromPlayer = 26.0f;
		}
		
		if(distanceFromPlayer > 122.0) {
			distanceFromPlayer = 122.0f;
		}
	}

	private void calculatePitch() {
		if (Mouse.isButtonDown(1)) {
			float pitchChange = Mouse.getDY() * 0.1f;
			pitch -= pitchChange;
			if(pitch > 90) {
				pitch = 90;
			}
			if(pitch < 0) {
				pitch = 0;
			}
		}
	}

	private void calculateAngleAroundPlayer() {
		if (Mouse.isButtonDown(0)) {
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
		}
	}
}
