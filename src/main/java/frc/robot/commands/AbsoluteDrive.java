// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.SwerveSubsystem;
import java.util.List;
import java.util.function.DoubleSupplier;
import swervelib.SwerveController;
import swervelib.math.SwerveMath;

/**
 * An example command that uses an example subsystem.
 */
public class AbsoluteDrive extends Command {

  private final SwerveSubsystem swerve;

  private final XboxController driverXbox;

  private boolean initRotation = false;

  private double speed_mult = 0.4;
  private double speed_mult_cbrt = Math.cbrt(speed_mult);

  /**
   * Used to drive a swerve robot in full field-centric mode. vX and vY supply
   * translation inputs, where x is
   * torwards/away from alliance wall and y is left/right. headingHorzontal and
   * headingVertical are the Cartesian
   * coordinates from which the robot's angle will be derived— they will be
   * converted to a polar angle, which the robot
   * will rotate to.
   *
   * @param swerve            The swerve drivebase subsystem.
   * @param vX                DoubleSupplier that supplies the x-translation
   *                          joystick input. Should be in the range -1
   *                          to 1 with deadband already accounted for. Positive X
   *                          is away from the alliance wall.
   * @param vY                DoubleSupplier that supplies the y-translation
   *                          joystick input. Should be in the range -1
   *                          to 1 with deadband already accounted for. Positive Y
   *                          is towards the left wall when
   *                          looking through the driver station glass.
   * @param headingHorizontal DoubleSupplier that supplies the horizontal
   *                          component of the robot's heading angle. In the
   *                          robot coordinate system, this is along the same axis
   *                          as vY. Should range from -1 to 1 with
   *                          no deadband. Positive is towards the left wall when
   *                          looking through the driver station
   *                          glass.
   * @param headingVertical   DoubleSupplier that supplies the vertical component
   *                          of the robot's heading angle. In the
   *                          robot coordinate system, this is along the same axis
   *                          as vX. Should range from -1 to 1
   *                          with no deadband. Positive is away from the alliance
   *                          wall.
   */
  public AbsoluteDrive(SwerveSubsystem swerve, XboxController driverXbox, double speed_multiplier) {
    this.swerve = swerve;
    this.driverXbox = driverXbox;

    this.speed_mult = speed_multiplier;
    this.speed_mult_cbrt = Math.cbrt(speed_multiplier);

    addRequirements(swerve);
  }

  @Override
  public void initialize() {
    initRotation = true;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double vX = Math.cbrt(MathUtil.applyDeadband(driverXbox.getLeftY(), OperatorConstants.LEFT_Y_DEADBAND) * -speed_mult);
    double vY = Math.cbrt(MathUtil.applyDeadband(driverXbox.getLeftX(), OperatorConstants.LEFT_X_DEADBAND) * -speed_mult);

    double total_mag = Math.sqrt(vX*vX + vY * vY);
    
    if (total_mag > speed_mult_cbrt) {
      vX /= (total_mag / speed_mult_cbrt);
      vY /= (total_mag / speed_mult_cbrt);
    }

    double headingHorizontal = -driverXbox.getRightX();
    double headingVertical = -driverXbox.getRightY();

    // Get the desired chassis speeds based on a 2 joystick module.
    ChassisSpeeds desiredSpeeds = swerve.getTargetSpeeds(vX, vY,
        headingHorizontal,
        headingVertical);

    // Prevent Movement After Auto
    if (initRotation) {
      if (headingHorizontal == 0 && headingVertical == 0) {
        // Get the curretHeading
        Rotation2d firstLoopHeading = swerve.getHeading();

        // Set the Current Heading to the desired Heading
        desiredSpeeds = swerve.getTargetSpeeds(0, 0, firstLoopHeading.getSin(), firstLoopHeading.getCos());
      }
      // Dont Init Rotation Again
      initRotation = false;
    }

    // Limit velocity to prevent tippy
    Translation2d translation = SwerveController.getTranslation2d(desiredSpeeds);
    translation = SwerveMath.limitVelocity(translation, swerve.getFieldVelocity(), swerve.getPose(),
        Constants.LOOP_TIME, Constants.ROBOT_MASS, List.of(Constants.CHASSIS),
        swerve.getSwerveDriveConfiguration());
    SmartDashboard.putNumber("LimitedTranslation", translation.getX());
    SmartDashboard.putString("Translation", translation.toString());

    // Make the robot move
    swerve.drive(translation, desiredSpeeds.omegaRadiansPerSecond, true);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

}
