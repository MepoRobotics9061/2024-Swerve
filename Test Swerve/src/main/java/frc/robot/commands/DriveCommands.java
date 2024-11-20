package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public final class DriveCommands{
  private DriveCommands()
  {
  }

  public static Command driveOne(
   Swerve s_Swerve,
   DoubleSupplier translationSup,
   DoubleSupplier strafeSup,
   DoubleSupplier rotationSup,
   BooleanSupplier robotCentricSup
  )
  {
    return Commands.run(() -> {

   SlewRateLimiter translationLimiter = new SlewRateLimiter(3.0);
   SlewRateLimiter strafeLimiter = new SlewRateLimiter(3.0);
   SlewRateLimiter rotationLimiter = new SlewRateLimiter(3.0);



    double translationVal =
        translationLimiter.calculate(
          MathUtil.applyDeadband(translationSup.getAsDouble(), Constants.Swerve.stickDeadband));
    double strafeVal =
        strafeLimiter.calculate(
            MathUtil.applyDeadband(strafeSup.getAsDouble(), Constants.Swerve.stickDeadband));
    double rotationVal =
        rotationLimiter.calculate(
            MathUtil.applyDeadband(rotationSup.getAsDouble(), Constants.Swerve.stickDeadband));

    /* Drive */
    s_Swerve.drive(
        new Translation2d(translationVal, strafeVal).times(Constants.Swerve.maxSpeed),
        rotationVal * Constants.Swerve.maxAngularVelocity,
        robotCentricSup.getAsBoolean(),
        true);
    }, s_Swerve);
  }

}