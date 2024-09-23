package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase{
    private CANSparkMax intake_motor = new CANSparkMax(20, MotorType.kBrushless);

    public IntakeSubsystem() {
        intake_motor.restoreFactoryDefaults();
        intake_motor.setInverted(false);
        intake_motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    public void setIntakeSpeed(double speed) {
        intake_motor.set(speed);
    }

    public void stopIntake() {
        intake_motor.set(0);
    }

    @Override
    public void periodic() {
        // Significantly slow the motor if we are drawing too much current (pushing note against shooter)
        if (intake_motor.getOutputCurrent() > 1.0 && intake_motor.get() > 0.2) {
            intake_motor.set(0.2);
        }
    }
}