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
    intakeSubsystem.stopIntake();
    shooterSubsystem.setShooterSpeed(5000, 5000);
    spunUp = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Wait for shooter to spin up before turning on intake
    if (shooterSubsystem.atFullSpeed()) {
      spunUp = true;
    }

    // Turn on intake if shooter is spun up
    if (spunUp) {
      intakeSubsystem.setIntakeSpeed(-1.0);
    }

    // Tell the intake subsystem that we fired the note
    if (spunUp && !intakeSubsystem.sensingNote()) {
      intakeSubsystem.setHasNote(false);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Stop shooter and intake
    shooterSubsystem.stopShooter();
    intakeSubsystem.stopIntake();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
    
}
