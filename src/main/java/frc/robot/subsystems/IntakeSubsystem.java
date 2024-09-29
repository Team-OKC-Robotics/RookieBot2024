package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase{
    private final CANSparkMax intake_motor = new CANSparkMax(10, MotorType.kBrushless);
    private final DigitalInput photoelectric_switch = new DigitalInput(0);

    private boolean has_note = false;

    private ShuffleboardTab tab = Shuffleboard.getTab("Driver");
    private GenericEntry hasNoteEntry = tab.add("Has Note", false).getEntry();

    public IntakeSubsystem() {
        intake_motor.restoreFactoryDefaults();
        intake_motor.setInverted(false);
        intake_motor.setIdleMode(CANSparkMax.IdleMode.kCoast);
    }

    public void setIntakeSpeed(double speed) {
        intake_motor.set(speed);
    }

    public boolean sensingNote() {
        return !photoelectric_switch.get();
    }

    public boolean hasNote() {
        return this.has_note;
    }

    public void stopIntake() {
        intake_motor.set(0);
    }

    public void setHasNote(boolean has_note) {
        this.has_note = has_note;
    }

    @Override
    public void periodic() {
        hasNoteEntry.setBoolean(sensingNote());
    }
}