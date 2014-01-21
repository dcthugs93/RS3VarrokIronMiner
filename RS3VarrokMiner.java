package scripts;

import java.awt.Color;
import java.awt.Point;
import java.awt.*;
import java.text.DecimalFormat;

import org.tribot.api.General;
import org.tribot.api.Screen;
import org.tribot.api.rs3.*;
import org.tribot.api.rs3.types.*;
import org.tribot.api.input.*;
import org.tribot.api.rs3.Skills;
import org.tribot.api.rs3.Skills.*;
import org.tribot.api.rs3.util.ThreadSettings;
import org.tribot.api.rs3.util.ThreadSettings.MODEL_CLICKING_METHOD;
import org.tribot.script.EnumScript;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;
import org.tribot.api.Timing;
import org.tribot.api.util.Restarter;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;


@ScriptManifest(authors = { "dcthugs93" }, category = "Mining", name = "RS3 Iron Mining E-Varrok")
public class RS3VarrokMiner extends EnumScript<RS3VarrokMiner.STATE> implements Painting
{

	public static 					Font font = AddFont.createFont();
	private final 					Color color = new Color(0,0,0);
	private static				    EGWPosition PlayerPOS = EGW.getPosition();
	public static final 			EGWPosition Bank_Pos = new EGWPosition (3253, 3421, 0),
									Mine_Pos = new EGWPosition (3285, 3370, 0);
	public static final long[] 		banker = {3167445700L},
									ironRocks = {1827131691L, 366615370L, 182193652L}; 
	public static final int[] 		loginScreen = {21234, 46069};
	public static final int 		inventoryFull = 24078;
	public static int 				mouseSpeed = General.random(65, 155),
									ironOres = 0;
	private static final long 		startTime = System.currentTimeMillis();
	private static 					String Status = "";
	
	
	enum STATE
	{
		CHECK, WALK_TO_IRON, WALK_TO_BANK, BANK, MINE_IRON
	}
	
		@Override
	public STATE getInitialState()
	{
		ThreadSettings.get().setScreenModelClickingMethod(MODEL_CLICKING_METHOD.CENTRE);
		AntiBan.startAntiBan();		
		Mouse.setSpeed(General.random(165, 175));


		return STATE.CHECK;
	}

	@Override
	public STATE handleState(STATE t)
	{
		switch (t)
		{
			case CHECK:
			ScreenModel[] iron = ScreenModels.findNearest(ironRocks);  

			try {
				if (font == null) {
					font = AddFont.createFont();
                }
           } catch (Exception e) {
				e.printStackTrace();
           }
			if(loggedOut()){ 	
				Restarter.restartClient(); 
			}
			if(PlayerPOS.distance(Mine_Pos) > 10 && !inventoryIsFull()){
				Status = "Walking to Iron Rocks";
				return STATE.WALK_TO_IRON;
			}
			if(PlayerPOS.distance(Mine_Pos) < 10 && iron.length > 0 && !inventoryIsFull()) {
				Status = "Mining Iron";
				return STATE.MINE_IRON;
			}
			if(inventoryIsFull()){
				if(PlayerPOS.distance(Bank_Pos) < 6){
					Status = "Depositing Iron Ore";
					return STATE.BANK;
				}
				if(PlayerPOS.distance(Bank_Pos) > 15){
					Status = "Walking To Bank";
					return STATE.WALK_TO_BANK;
				}
			}					
			
			case MINE_IRON:
			if(PlayerPOS.distance(Mine_Pos) < 8){
					MineIron.mineIron();
					if(!inventoryIsFull()){
						return STATE.MINE_IRON;
					}
			}
			return STATE.CHECK;
			
			case WALK_TO_BANK:
			if(PlayerPOS.distance(Bank_Pos) > 6){
				WalkingMethods.walkToBank();
			}
			if(PlayerPOS.distance(Bank_Pos) <= 6){
				return STATE.BANK;
			}
			return STATE.WALK_TO_BANK;
			
			case BANK:
			while(Backpack.find(ironOres).length >= 1){
				Methods.bank();
			} 
			return STATE.WALK_TO_IRON;
			
			case WALK_TO_IRON:
			if(PlayerPOS.distance(Mine_Pos) > 6){
				WalkingMethods.walkToMine();
			}
			if(PlayerPOS.distance(Mine_Pos) <= 6){
				return STATE.MINE_IRON;
			}
			return STATE.WALK_TO_IRON;			

		}
		return STATE.CHECK;
	}
	
	/*Backpack.isFull() is broken so I use this*/
	public static boolean inventoryIsFull(){
		Texture[] inventory = Textures.find(inventoryFull);
		return inventory.length > 0;
	}
	
	public static boolean loggedOut(){
		Texture[] loggedOut = Textures.find(loginScreen);
		return loggedOut.length > 0;
	}
	
	    @Override
    public void onPaint(Graphics g) {
		
        long timeRan = System.currentTimeMillis() - startTime;
		
		
		DecimalFormat df = new DecimalFormat("#,###");
	
        g.setFont(font);
        g.setColor(color);
        g.drawString("Time Ran: " + Timing.msToString(timeRan), 10, 30);
		g.drawString("Status: " + Status + "  ", 10, 60);
		g.drawString("APX. Ores Mined: " + ironOres + "  ", 10, 90);
		g.drawString("This is in Beta Stage, Report Bugs", 10, 120);
		g.drawString("dcthugs93 RS3 Varrok Iron Miner", 10, 150);

    }	

	
}
		
	