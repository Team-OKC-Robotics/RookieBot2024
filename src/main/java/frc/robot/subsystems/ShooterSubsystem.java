package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase{
    private CANSparkMax shooter_motor = new CANSparkMax(20, MotorType.kBrushless);

    private RelativeEncoder encoder;

    public ShooterSubsystem() {
        shooter_motor.restoreFactoryDefaults();

        shooter_motor.setInverted(false);

        shooter_motor.setIdleMode(CANSparkMax.IdleMode.kCoast);

        encoder = shooter_motor.getEncoder();
    }

    public void setShooterSpeed(double speed) {
        shooter_motor.set(speed);
    }

    public double getShooterSpeed() {
        return shooter_motor.get();
    }
}
