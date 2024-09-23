package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class IntakeCommand extends Command{
    ShooterSubsystem shooterSubsystem;
    IntakeSubsystem intakeSubsystem;

    public IntakeCommand(ShooterSubsystem shooterSubsystem, IntakeSubsystem intakeSubsystem) {
        this.shooterSubsystem = shooterSubsystem;
        this.intakeSubsystem = intakeSubsystem;

        addRequirements(shooterSubsystem, intakeSubsystem);
    }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Stop shooter so we don't accidentally shoot the note we are intaking
    shooterSubsystem.stopShooter();
    intakeSubsystem.setIntakeSpeed(0.7);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Do nothing, just wait for end() when the user lets go of the button
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intakeSubsystem.stopIntake();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
    
}
