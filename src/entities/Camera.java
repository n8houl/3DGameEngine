package entities;

import org.lwjgl.util.vector.Vector3f;

public class Camera {
	private Vector3f position = new Vector3f(100, 35, 50);
	private float pitch = 10, yaw = 0, roll;
	
	public void move() {
		
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
	public void setRoll(float roll) {
		this.roll = roll;
	}
}
