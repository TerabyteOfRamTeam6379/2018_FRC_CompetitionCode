package org.usfirst.frc.team6379.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.command.Command;

public class Robot extends IterativeRobot {
	Spark leftFront1 = new Spark(0);
	Spark leftBack1 = new Spark(1);
	Spark rightFront1 = new Spark(2);
	Spark rightBack1 = new Spark(3);
	Spark LiftMotor1 = new Spark(4);
  
  MecanumDrive myRobot = new MecanumDrive(this.leftFront1, this.leftBack1, this.rightFront1, this.rightBack1);
  
  Joystick leftStick = new Joystick(0);
  Button button1 = new JoystickButton(this.leftStick, 1);
  Timer timer = new Timer();
  CameraServer server;
  
  DigitalInput forwardLimitSwitch, reverseLimitSwitch1;
  DigitalInput forwardLimitSwitch1 = new DigitalInput(1);
  DigitalInput reverseLimitSwitch = new DigitalInput(2);
  
  public Robot()
  {
    this.myRobot.setExpiration(0.10);
  }
  @Override
  public void robotInit()
  {
    CameraServer.getInstance().startAutomaticCapture();
  }
  @Override
  public void autonomousInit()
  {
    this.timer.reset();
    this.timer.start();
  }
  
  
  @Override
  public void autonomousPeriodic()
  {
    String gameData = DriverStation.getInstance().getGameSpecificMessage();
    
    while (this.timer.get() <= 7.586) { //Drives forward for 7.586 seconds until the robot theoretically drives near the middle of the Platform Zone.
  	  this.myRobot.driveCartesian(0.5, 0.5, 0.0);
    }
    
    if (gameData.charAt(1) == 'L')//identifies lever as (1) on the left side (L)//
    {
    //then//	
      if (this.timer.get() < 10.105) { //drive forward line 48's time + x time for distance to reach scale.
        this.myRobot.driveCartesian(0.5, 0.5, 0.0);
      } else { //if robot reaches the scale
        this.myRobot.driveCartesian(0.0, 0.0, 0.0);
        //turn 90deg right "this.myRobot.driveCartesian(0.0,0.0,rotationSpeed);"
        //drop powerCube
      }
      
    }
    
    else { //if the scale isn't 'L', therefore the scale is right.
    	//turn 90 deg right
    	if (this.timer.get() < 11.255) {
    		this.myRobot.driveCartesian(0.5, 0.5, 0.0); //drive across Platform Zone
    		}
    	//turn 90deg left "this.myRobot.driveCartesian(0.0,0.0,rotationSpeed);"
    	else if (this.timer.get() <13.774) {
    		this.myRobot.driveCartesian(0.5, 0.5, 0.0); //drive to side of scale
    		}
    	else {
    		this.myRobot.driveCartesian(0.0, 0.0, 0.0);
    		}
    	//turn 90deg left "this.myRobot.driveCartesian(0.0,0.0,rotationSpeed);"
    	//drop powerCube
    	}
  }
  
  
  @Override
  public void teleopInit() {}
  
  
  @Override
  public void teleopPeriodic()
  {
	  {
      this.myRobot.setSafetyEnabled(true);
      
      double ySpeed, xSpeed, zRot;
      
      // DEADZONES, Change intensity by changing if conditions
      if ((leftStick.getRawAxis(0) < 0.1) && (leftStick.getRawAxis(0) > -0.1)) {
    	  ySpeed = 0;
      } else {
    	  ySpeed = leftStick.getRawAxis(0);
      }
      
      if ((leftStick.getRawAxis(1) < 0.1) && (leftStick.getRawAxis(1) > -0.1)) {
    	  xSpeed = 0;
      } else {
    	  xSpeed = leftStick.getRawAxis(1);
      }
      
      
      //TRIGGER CONTROLS
      if (leftStick.getRawButton(1)) {
    	  zRot = leftStick.getRawAxis(5) * 0.6;
      } else {
    	  zRot = leftStick.getRawAxis(5);
      }
      
      
      //LIFT CONTROLS
      if ((leftStick.getRawAxis(2) < 0.1) && (leftStick.getRawAxis(2) > -0.1))  {
    	   LiftMotor1.setSpeed(0);
      } else {
    	  LiftMotor1.setSpeed(.5);
      }
      
     
     
     //LIMIT SWITCHES(test first)
      
       /**if (forwardLimitSwitch.get()) // If the forward limit switch is pressed, we want to keep the values between -1 and 0
            LiftMotor1.setSpeed(0);
        else if(reverseLimitSwitch.get()) // If the reversed limit switch is pressed, we want to keep the values between 0 and 1
        	LiftMotor1.setSpeed(0);
        else {
    	    LiftMotor1.setSpeed(.5);
        }**/
	  
       
       
        
      
      this.myRobot.driveCartesian( ySpeed, -xSpeed, zRot);
     
      Timer.delay(0.005);
    }
  }
  
  
  
  
  
  @Override
  public void testPeriodic() {
		LiveWindow.run();
	}

	
}