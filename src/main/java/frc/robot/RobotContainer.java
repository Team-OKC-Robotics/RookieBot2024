// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.AbsoluteDrive;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.ShootCommand;
import frc.robot.commands.TimedIntakeCommand;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveSubsystem;
import java.io.File;


public class RobotContainer
{

  final XboxController driverXbox = new XboxController(0);
  private final SwerveSubsystem drivebase = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),
                                                                         "swerve"));
  private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
  private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();

  private final ShootCommand shootCommand = new ShootCommand(shooterSubsystem, intakeSubsystem);
  private final IntakeCommand intakeCommand = new IntakeCommand(shooterSubsystem, intakeSubsystem);
  private final TimedIntakeCommand pullBackCommand = new TimedIntakeCommand(shooterSubsystem, intakeSubsystem, 0.5);

  private final JoystickButton driver_b_button = new JoystickButton(driverXbox, 1);
  private final JoystickButton driver_left_bumper_button = new JoystickButton(driverXbox, 5);
  private final JoystickButton driver_right_bumper_button = new JoystickButton(driverXbox, 6);

  // private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();

  //Makes Auto Chooser
  private SendableChooser<String> autoChooser = new SendableChooser<String>();
  private ShuffleboardTab tab = Shuffleboard.getTab("auto chooser");

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
    driver_b_button.onTrue((Commands.runOnce(drivebase::zeroGyro)));
    driver_left_bumper_button.whileTrue(intakeCommand).onFalse(pullBackCommand);
    driver_right_bumper_button.whileTrue(shootCommand);
  }


  public Command getAutonomousCommand()
  {
    return null;
  }
}