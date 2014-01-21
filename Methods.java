package scripts;

import org.tribot.api.input.Mouse;
import org.tribot.api.rs3.*;
import org.tribot.api.rs3.types.*;
import org.tribot.api.rs3.util.ThreadSettings;
import org.tribot.api.rs3.util.ThreadSettings.MODEL_CLICKING_METHOD;
import org.tribot.api.General;
import org.tribot.api.DynamicClicking;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;


public class Methods{

	/* These methods may not look great but the fail safe would be in the main class within the states.*/
	
	public static void bank() {
		ScreenModel[] banker = ScreenModels.findNearest(RS3VarrokMiner.banker); 
		ScreenModel banker1 = RandomizedClicking.getClosestModel(RS3VarrokMiner.banker);
		if (banker.length > 0)	{	
			if(banker[0].isClickable(MODEL_CLICKING_METHOD.CENTRE)){
				Mouse.setSpeed(RS3VarrokMiner.mouseSpeed);
				General.sleep(500, 700);
				if(RandomizedClicking.clickScreenModel(banker1, "Bank", -4, 8, -4, 6)){
					Timing.waitCondition(new Condition() {
						public boolean active() {
							return Banking.isBankScreenOpen();
						}
					}, General.random(500, 1000));
				}
			}RandomizedCameraMovements.randomCameraRotation(); General.sleep(1500, 2000);	
			if(Banking.isBankScreenOpen()){
				Banking.depositBackpack();
				General.sleep(2000, 3000);
				Banking.close();
			}
		}
		Mouse.setSpeed(RS3VarrokMiner.mouseSpeed);
        WebWalking.walkTo(RS3VarrokMiner.Bank_Pos);
        while (Player.isMoving())
			General.sleep(800, 1000);		
    }
}