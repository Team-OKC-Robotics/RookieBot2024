package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase{
    private CANSparkMax left_shooter_motor = new CANSparkMax(20, MotorType.kBrushless);
    private CANSparkMax right_shooter_motor = new CANSparkMax(21, MotorType.kBrushless);

    private RelativeEncoder left_encoder;
    private RelativeEncoder right_encoder;

    public ShooterSubsystem() {
        left_shooter_motor.restoreFactoryDefaults();
        right_shooter_motor.restoreFactoryDefaults();

        left_shooter_motor.setInverted(false);
        right_shooter_motor.setInverted(false);

        left_shooter_motor.setIdleMode(CANSparkMax.IdleMode.kCoast);
        right_shooter_motor.setIdleMode(CANSparkMax.IdleMode.kCoast);

        left_encoder = left_shooter_motor.getEncoder();
        right_encoder = right_shooter_motor.getEncoder();
    }

    public void setShooterSpeed(double speed) {
        left_shooter_motor.set(speed);
        right_shooter_motor.set(speed);
    }

    public double getShooterSpeed() {
        return left_shooter_motor.get();
    }
}