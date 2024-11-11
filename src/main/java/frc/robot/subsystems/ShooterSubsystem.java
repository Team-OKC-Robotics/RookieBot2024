package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase{
    private CANSparkMax left_shooter_motor = new CANSparkMax(12, MotorType.kBrushless);
    private CANSparkMax right_shooter_motor = new CANSparkMax(11, MotorType.kBrushless);

    private RelativeEncoder left_encoder;
    private RelativeEncoder right_encoder;

    private SparkPIDController right_PID_controller;
    private SparkPIDController left_PID_controller;

    private double left_motor_setpoint = 0.0;
    private double right_motor_setpoint = 0.0;

    private final double PID_P = 0.0001;
    private final double PID_FF = 0.000199;
    private final double RPM_DIFF_THRESHOLD = 200;

    private ShuffleboardTab tab = Shuffleboard.getTab("Driver");
    private GenericEntry leftRPMEntry = tab.add("Left RPM", 0.0).getEntry();
    private GenericEntry rightRPMEntry = tab.add("Right RPM", 0.0).getEntry();

    public ShooterSubsystem() {
        left_shooter_motor.restoreFactoryDefaults();
        right_shooter_motor.restoreFactoryDefaults();

        left_shooter_motor.setInverted(true);
        right_shooter_motor.setInverted(false);

        left_shooter_motor.setIdleMode(CANSparkMax.IdleMode.kCoast);
        right_shooter_motor.setIdleMode(CANSparkMax.IdleMode.kCoast);

        left_encoder = left_shooter_motor.getEncoder();
        right_encoder = right_shooter_motor.getEncoder();

        left_PID_controller = left_shooter_motor.getPIDController();
        right_PID_controller = right_shooter_motor.getPIDController();

        // Ramp up the shooter motors to full speed over 1.5 seconds
        left_shooter_motor.setClosedLoopRampRate(1.2);
        right_shooter_motor.setClosedLoopRampRate(1.2);
    
        // Prevent the motors from running in reverse
        left_PID_controller.setOutputRange(0.0, 1.0);
        right_PID_controller.setOutputRange(0.0, 1.0);
    
        left_PID_controller.setFeedbackDevice(left_encoder);
        right_PID_controller.setFeedbackDevice(right_encoder);

        left_PID_controller.setP(PID_P);
        right_PID_controller.setP(PID_P);

        left_PID_controller.setFF(PID_FF);
        right_PID_controller.setFF(PID_FF);
    }

    // TODO: Create a function that sets the speed of the shooter motors
    // The function should have two parameters: the left shooter speed and the right shooter speed
    // The left_motor_setpoint and right_motor_setpoint should be set and
    // then setPIDReferences() should be called.



    // TODO: Create a function that stops the motors. Does this function need any parameters?
    // The left_motor_setpoint and right_motor_setpoint should be set and
    // then setPIDReferences() should be called.



    // TODO: Create a function that
    //   Returns TRUE if BOTH the left and the right motor speeds are within some tolerance of the left and right motor setpoints
    //   Returns FALSE otherwise
    // Hint: left_encoder.getVelocity() will get you the current speed of the left motor
    // Hint: RPM_DIFF_THRESHOLD is the max difference between the current speed and setpoint speed we want to tolerate.
    //   How will you handle both cases where the current speed can be above or below the setpoint?



    
    private void setPIDReferences() {
        left_PID_controller.setReference(left_motor_setpoint, CANSparkMax.ControlType.kVelocity);
        right_PID_controller.setReference(right_motor_setpoint, CANSparkMax.ControlType.kVelocity);
    }

    @Override
    public void periodic() {
        leftRPMEntry.setDouble(left_encoder.getVelocity());
        rightRPMEntry.setDouble(right_encoder.getVelocity());
    }
}