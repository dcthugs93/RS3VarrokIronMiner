package scripts;

import org.tribot.api.input.Mouse;
import org.tribot.api.rs3.Player;
import org.tribot.api.rs3.types.EGWPosition;
import org.tribot.api.rs3.EGW;
import org.tribot.api.rs3.util.ThreadSettings;
import org.tribot.api.rs3.util.ThreadSettings.MODEL_CLICKING_METHOD;
import org.tribot.api.General;
import org.tribot.api.rs3.ScreenModels;
import org.tribot.api.rs3.types.ScreenModel;
import org.tribot.api.rs3.types.*;
import org.tribot.api.rs3.*;


public class MineIron{
	
	public static void mineIron()  {
		TextChar[] oreMine = Text.findCharsInArea(5, 465, 270, 150, false);
			String x = Text.lineToString(oreMine, 10000);
			boolean b = x.contains("YYoouummaa");
		ScreenModel[] iron = ScreenModels.findNearest(RS3VarrokMiner.ironRocks);  
		ScreenModel iron1 = RandomizedClicking.getClosestModel(RS3VarrokMiner.ironRocks);
		EGWPosition PlayerPOS = EGW.getPosition();
			
		if (iron.length > 0)	{	
			General.println("Found Mining Spot");
			if(iron[0].isClickable(MODEL_CLICKING_METHOD.CENTRE)){		
				General.println("The Iron Ore is clickable");
				Mouse.setSpeed(RS3VarrokMiner.mouseSpeed);
				if(RandomizedClicking.clickScreenModel(iron1, -2, 6, -3, 9)){
					General.println("click successful");
					General.sleep(1000, 3000);
				}
				while(Player.getAnimation() > 0 || Player.isMoving()) { 
					if(b){
						RS3VarrokMiner.ironOres++;
					}
					General.sleep(1000, 3000);
				}

			}
			RandomizedCameraMovements.randomCameraRotation(); General.sleep(1500, 2000);
			
		}
	}
}