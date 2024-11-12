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

    private ShuffleboardTab tab = Shuffleboard.getTab("Driver");
    private GenericEntry hasNoteEntry = tab.add("Has Note", false).getEntry();

    public IntakeSubsystem() {
        intake_motor.restoreFactoryDefaults();
        intake_motor.setInverted(true);
        intake_motor.setIdleMode(CANSparkMax.IdleMode.kCoast);
    }

    // TODO: Create a function that sets the speed of the intake motor
    // Hint: Use the .set() function of the intake_motor. You do not need to use the PID
    // functions like in the ShooterSubsystem
    public void setIntakeSpeed(double intakeSpeed) {
        intake_motor.set(intakeSpeed);
    }


    // TODO: Create a function that stops the intake motor
    public void stopIntake() {
        intake_motor.set(0);
    }

    
    public boolean sensingNote() {
        return !photoelectric_switch.get();
    }

    @Override
    public void periodic() {
        hasNoteEntry.setBoolean(sensingNote());
    }
}
