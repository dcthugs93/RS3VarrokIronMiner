package scripts;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.awt.Point;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Filter;
import org.tribot.script.Script;

/* This is Lan Anti-Ban, I am still altering this class for RS3. I do not take credit for this*/

enum EventScale {
        // Small events happen between 20 and 45 seconds.
        SMALL_EVENT,

        // Large events happen between 55 and 90 seconds.
        LARGE_EVENT,
};

enum Events {
        MOVE_MOUSE (true, new AntiBanEvent(new MoveMouse(), EventScale.SMALL_EVENT)),
        ROTATE_CAMERA (true, new AntiBanEvent(new RotateCamera(), EventScale.SMALL_EVENT)),
		MOVE_MOUSE_OS (true, new AntiBanEvent(new MoveMouseOS(), EventScale.SMALL_EVENT)),
        SHORT_AFK (true, new AntiBanEvent(new ShortAFK(), EventScale.LARGE_EVENT));
        
        private boolean enable;
        private final AntiBanEvent event;

        Events(boolean enable, AntiBanEvent event) {
                this.enable = enable;
                this.event = event;
        }
        
	
	
        public boolean isEnabled() { return enable; }
        public void setEnabled(boolean enable) { this.enable = enable; }
    
        public AntiBanEvent getEvent() { return event; }
};

public class AntiBan {

        private static AntiBanEvent lastSmallEvent = Events.MOVE_MOUSE.getEvent();
        private static AntiBanEvent lastLargeEvent = Events.SHORT_AFK.getEvent();
        
        private static ScheduledExecutorService executor;
        private static boolean enabled = false;
        
        private static boolean writeLog = true;
        
		public static void mouseOffScreen(int time) { 
        int side = General.random(0, 3);
        switch (side) {

        case 0:
            Mouse.move(0, General.random(40, 400));
                General.println("Mouse Moving off screen to the left side");
            break;
        case 1:
            Mouse.move(764, General.random(40, 400)); 
                General.println("Mouse Moving off screen to the right side");
            break;
        case 2:
            Mouse.move(General.random(60, 685), 0);
                General.println("Mouse Moving off screen to the top");
				break;
        case 3:
            Mouse.move(General.random(60, 685), 498);
                General.println("Mouse Moving off screen to the bottom");
            break;
        }
        General.sleep(time);
    }
		
        public static boolean getWriteLog() {
                return writeLog;
        }

        public static void setWriteLog(boolean writeLog) {
                AntiBan.writeLog = writeLog;
        }
        
        public static void startAntiBan() {
                if (!enabled){
                        executor = Executors.newSingleThreadScheduledExecutor();
                        enabled = true;
                        scheduleSmallEvent();
                        scheduleLargeEvent();
                }
        }
        
        public static void stopAntiBan() {
                if (enabled){
                        General.println("Stopping AntiBan.");
                        executor.shutdown();
                        enabled = false;
                }
        }
        
        public static void useEvent(Events event, boolean use) {
                event.setEnabled(use);
        }
        
        public static void scheduleSmallEvent() {
                if (enabled) {
                        ArrayList<AntiBanEvent> smallTasks = new ArrayList<AntiBanEvent>();
                        
                        for (Events e : Events.values()) {
                                if (e.isEnabled() && e.getEvent().getScale() == EventScale.SMALL_EVENT) {
                                        smallTasks.add(e.getEvent());
                                }
                        }
                        
                        if (smallTasks.size() > 0) {
                                AntiBanEvent nextSmallTask = null;
                                while (nextSmallTask == null || (nextSmallTask == lastSmallEvent.getEvent() && smallTasks.size() > 1 ))
                                        nextSmallTask = smallTasks.get(General.random(0, smallTasks.size() - 1));
                
                                int schedule = General.random(30, 55);
                                executor.schedule(nextSmallTask, schedule, TimeUnit.SECONDS);
                                lastSmallEvent = nextSmallTask;
                                if (getWriteLog())
                                        General.println("AntiBan: Scheduled "+ nextSmallTask.getEvent().getClass().getSimpleName()+ " to happen in: "+schedule+ " seconds.");
                        }
                }
        }

        public static void scheduleLargeEvent() {
                if (enabled) {
                        ArrayList<AntiBanEvent> largeTasks = new ArrayList<AntiBanEvent>();

                        for (Events e : Events.values()) {
                                if (e.isEnabled() && e.getEvent().getScale() == EventScale.LARGE_EVENT) {
                                        largeTasks.add(e.getEvent());
                                }
                        }
                        
                        if (largeTasks.size() > 0) {
                                // Next task can never be the same as the previous task.
                                AntiBanEvent nextLargeTask = null;
                                while (nextLargeTask == null || (nextLargeTask == lastLargeEvent.getEvent() && largeTasks.size() > 1 ))
                                        nextLargeTask = largeTasks.get(General.random(0, largeTasks.size() - 1));
        
                                int schedule = General.random(55, 90);
                                executor.schedule(nextLargeTask, schedule, TimeUnit.SECONDS);
                                lastLargeEvent = nextLargeTask;
                                if (getWriteLog())
                                        General.println("AntiBan: Scheduled "+nextLargeTask.getEvent().getClass().getSimpleName()+" to happen in: "+schedule+" seconds.");
                        }
                }
        }
};


class AntiBanEvent implements Runnable {
        
        private final Runnable event;
        private final EventScale scale;
        public EventScale getScale() { return scale; }
        public Runnable getEvent() { return event; }

        AntiBanEvent(Runnable event, EventScale scale) {
                this.event = event;
                this.scale = scale;
        }
        
        public void run() {
                event.run();
                
                if (scale == EventScale.LARGE_EVENT)
                        AntiBan.scheduleLargeEvent();
                else
                        AntiBan.scheduleSmallEvent();
        }
};

class MoveMouse implements Runnable {
        @Override
        public void run() {
				
                Mouse.move(General.random(60, 300), General.random(40, 450));
				
        }
};

class MoveMouseOS implements Runnable {
        @Override
        public void run() {
				
                AntiBan.mouseOffScreen(General.random(1500, 3000));
				
        }
};

class RotateCamera implements Runnable {
        @Override
        public void run() {
                RandomizedCameraMovements.randomCamera();
				
        }
};

class ShortAFK implements Runnable {
        @Override
        public void run() {
                
                if (new Random().nextBoolean())
                        Mouse.move(-1, General.random(0, 900));
                
                int timeToAFK = General.random(8000, 16000);
                while (timeToAFK > 0) {
                        General.sleep(100);
                        timeToAFK =- 100;
                }
        }
};

