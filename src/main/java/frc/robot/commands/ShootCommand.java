package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootCommand extends Command{
    ShooterSubsystem _shooterSubsystem;

    public ShootCommand(ShooterSubsystem shooterSubsystem) {
        this._shooterSubsystem = shooterSubsystem;
        addRequirements(shooterSubsystem);
    }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    _shooterSubsystem.setShooterSpeed(0.5);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    _shooterSubsystem.setShooterSpeed(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
    
}
