package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class OutakeCommand extends Command{
    ShooterSubsystem shooterSubsystem;
    IntakeSubsystem intakeSubsystem;

    public OutakeCommand(ShooterSubsystem shooterSubsystem, IntakeSubsystem intakeSubsystem) {
        this.shooterSubsystem = shooterSubsystem;
        this.intakeSubsystem = intakeSubsystem;

        addRequirements(shooterSubsystem, intakeSubsystem);
    }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // TODO: Stop shooter (so we don't accidentally shoot the note we are outtaking)
    shooterSubsystem.stopShooter();


    // TODO: Start outtaking
    intakeSubsystem.setIntakeSpeed(-0.5);


  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Do nothing, just wait for end() when the user lets go of the button
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // TODO: Stop outtaking once the command is finished (e.g. when the user lets go of the button)
    intakeSubsystem.stopIntake();

    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
    
}

