package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootCommand extends Command{
    ShooterSubsystem shooterSubsystem;
    IntakeSubsystem intakeSubsystem;

    boolean spunUp = false;

    public ShootCommand(ShooterSubsystem shooterSubsystem, IntakeSubsystem intakeSubsystem) {
        this.shooterSubsystem = shooterSubsystem;
        this.intakeSubsystem = intakeSubsystem;

        addRequirements(shooterSubsystem, intakeSubsystem);
    }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // TODO: Stop the intake
    intakeSubsystem.stopIntake();


    // TODO: Set the shooter speed to 5000 for both motors
    shooterSubsystem.setShooterSpeed(5000, 5000);


    // TODO: Set the spunUp variable to false
    // We will use this to keep track of when the shooter is ready to shoot
    spunUp = false;


  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // TODO: Check if the shooter is at the speed setpoint. If so, set spunUp to true
    if (shooterSubsystem.motorSpeedsWIthinBounds()) {
      spunUp = true;
    }


    // TODO: If spunUp is true, set the intake speed to send the note to the shooter

    if (spunUp) {
      intakeSubsystem.setIntakeSpeed(1);
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // TODO: Stop the intake motors AND stop the shooter motors
    intakeSubsystem.stopIntake();
    shooterSubsystem.stopShooter();

    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
