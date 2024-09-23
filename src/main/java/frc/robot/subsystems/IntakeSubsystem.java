package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase{
    private CANSparkMax intake_motor = new CANSparkMax(20, MotorType.kBrushless);

    private boolean has_note = false;

    private ShuffleboardTab tab = Shuffleboard.getTab("Driver");
    private GenericEntry hasNoteEntry = tab.add("Has Note", false).getEntry();

    public IntakeSubsystem() {
        intake_motor.restoreFactoryDefaults();
        intake_motor.setInverted(false);
        intake_motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

    public void setIntakeSpeed(double speed) {
        intake_motor.set(speed);

        // If we are intaking, assume we don't have a note anymore
        if (speed > 0.3) {
            has_note = false;
            hasNoteEntry.setBoolean(false);
        }
    }

    public void stopIntake() {
        intake_motor.set(0);
    }

    public boolean hasNote() {
        return has_note;
    }

    @Override
    public void periodic() {
        // Significantly slow the motor if we are drawing too much current.
        // Assume we also have a note now (??)
        if (intake_motor.getOutputCurrent() > 1.0 && intake_motor.get() > 0.2) {
            intake_motor.set(0.2);
            has_note = true;
            hasNoteEntry.setBoolean(true);
        }
    }
}