package scripts;

import org.tribot.api.input.Keyboard;
import org.tribot.api.General;


public class RandomizedCameraMovements{



	public static void randomizedCameraRotation(boolean leftTurn, int min, int max)
	{
		char left = 37;
		char right = 39;
		if(leftTurn){
			Keyboard.pressKey(left);
			General.sleep(min, max);
			Keyboard.releaseKey(left);
		}else if(!leftTurn){
			Keyboard.pressKey(right);
			General.sleep(min, max);
			Keyboard.releaseKey(right);
		}
	}
	
	public static void randomizedCameraAngle(boolean isGoingUp, int min, int max)
	{
		char up = 38;
		char down = 40;
		if(isGoingUp){
			Keyboard.pressKey(up);
			General.sleep(min, max);
			Keyboard.releaseKey(up);
		}else if(!isGoingUp){
			Keyboard.pressKey(down);
			General.sleep(min, max);
			Keyboard.releaseKey(down);
		}
	}
	
	public static void randomCamera(){
	       int k = General.random(1, 3);
        switch (k) {

        case 1:
            randomizedCameraRotation(true, General.random(600, 800), General.random(1000, 1500));
                General.println("Doing a left Rotation");
            break;
        case 2:
           randomizedCameraRotation(false, General.random(600, 800), General.random(1000, 1500));
                General.println("Doing a right rotation");
            break;
        case 3:
            randomizedCameraAngle(true, General.random(400, 550), General.random(600, 1000));
                General.println("Changing angle upwards");
				break;
        }
    }
	
		public static void randomCameraRotation(){
	       int k = General.random(1, 2);
        switch (k) {

        case 1:
            randomizedCameraRotation(true, General.random(600, 800), General.random(1000, 1500));
                General.println("Doing a left Rotation");
            break;
        case 2:
           randomizedCameraRotation(false, General.random(600, 800), General.random(1000, 1500));
                General.println("Doing a right rotation");
            break;
        }
    }
	
}