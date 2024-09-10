package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase{
    private CANSparkMax shooter_motor = new CANSparkMax(20, MotorType.kBrushless);

    public ShooterSubsystem() {

    }

    public void setShooterSpeed(double speed) {
        shooter_motor.set(speed);
    }
}
