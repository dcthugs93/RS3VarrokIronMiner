package scripts;

import org.tribot.api.input.Mouse;
import org.tribot.api.rs3.*;
import org.tribot.api.rs3.types.*;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;

public class WalkingMethods{

	private static EGWPosition PlayerPOS = EGW.getPosition();
	
	public static void walkToMine()  {

		if(PlayerPOS.distance(RS3VarrokMiner.Mine_Pos) > 3){
			WebWalking.walkTo(RS3VarrokMiner.Mine_Pos);
			General.println("Walking to V-Mines");
            Timing.waitCondition(new Condition() {
				public boolean active() {
					return PlayerPOS.distance(RS3VarrokMiner.Mine_Pos) < 4;
				}
			}, General.random(1000, 1500));
		}
	}
	
	public static void walkToBank()  {
	
		if(PlayerPOS.distance(RS3VarrokMiner.Bank_Pos) > 3){
			WebWalking.walkTo(RS3VarrokMiner.Bank_Pos);
			General.println("Walking to Bank");
            Timing.waitCondition(new Condition() {
				public boolean active() {
					return PlayerPOS.distance(RS3VarrokMiner.Bank_Pos) < 4;
				}
			}, General.random(1000, 1500));
		}
	}
}