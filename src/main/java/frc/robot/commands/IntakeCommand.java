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
    // TODO: Stop the shooter motors (so we don't accidentally shoot the note we are intaking)    
    shooterSubsystem.stopShooter();


    // TODO: Start the intake motors
    // Hint: Use a speed around 0.5 (or -0.5 depending on if the motor is backwards)
    intakeSubsystem.setIntakeSpeed(0.5);


  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // TODO: If we are sensing a note, stop the intake motors.
    // Hint: Use the sensingNote() function of the shooterSubsystem
    if (intakeSubsystem.sensingNote()) {
      intakeSubsystem.stopIntake();
    }


  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // TODO: Stop the intake motors
    intakeSubsystem.stopIntake();

    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // Stop intaking when we sense the note
    return intakeSubsystem.sensingNote();
  }
    
}
