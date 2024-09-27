// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import java.io.File;

import com.pathplanner.lib.commands.PathPlannerAuto;


public class RobotContainer
{

  final XboxController driverXbox = new XboxController(0);
  private final SwerveSubsystem drivebase = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),
                                                                         "swerve"));
  private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
  private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();

  private final ShootCommand shootCommand = new ShootCommand(shooterSubsystem, intakeSubsystem);
  private final IntakeCommand intakeCommand = new IntakeCommand(shooterSubsystem, intakeSubsystem);
  private final OutakeCommand outakeCommand = new OutakeCommand(shooterSubsystem, intakeSubsystem);
  private final TimedIntakeCommand pullBackCommand = new TimedIntakeCommand(shooterSubsystem, intakeSubsystem, 0.5);

  private final JoystickButton driver_a_button = new JoystickButton(driverXbox, 1);
  private final JoystickButton driver_left_bumper_button = new JoystickButton(driverXbox, 5);
  private final JoystickButton driver_right_bumper_button = new JoystickButton(driverXbox, 6);
  private final JoystickButton driver_b_button = new JoystickButton(driverXbox, 2);

  // private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();

  //Makes Auto Chooser
  private SendableChooser<String> autoChooser = new SendableChooser<String>();
  private ShuffleboardTab tab = Shuffleboard.getTab("Driver");

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer()
  {
    //Adding Auto Chooser Options
    autoChooser.setDefaultOption("Short Line One", "Short Line One");
    autoChooser.addOption("Short Line Two", "Short Line Two");

    configureBindings();

    tab.add(autoChooser);

    // Set default swerve drive command
    AbsoluteDrive closedAbsoluteDrive = new AbsoluteDrive(drivebase, driverXbox);
    drivebase.setDefaultCommand(closedAbsoluteDrive);
  }

  private void configureBindings()
  {
    driver_a_button.onTrue((Commands.runOnce(drivebase::zeroGyro)));
    driver_b_button.whileTrue(outakeCommand);
    driver_left_bumper_button.whileTrue(intakeCommand);
    driver_right_bumper_button.whileTrue(shootCommand);
  }

  public void zeroGyro() {
    drivebase.zeroGyro();
  }

  public Command getAutonomousCommand()
  {
    return new PathPlannerAuto(autoChooser.getSelected());
  }
}