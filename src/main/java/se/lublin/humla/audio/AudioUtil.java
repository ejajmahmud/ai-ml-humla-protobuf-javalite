package se.lublin.humla.audio;

import static java.lang.Math.abs;
import static java.lang.Math.log10;

public class AudioUtil {
    public static final double DBOFFSET = -74.0;
    public static final double LOWPASSFILTERTIMESLICE = 0.001;

    public static double calculateDecibelLevel(short[] buffer, int bufferSize) {
        double decibels = DBOFFSET;
        double currentFilteredValueOfSampleAmplitude = 0.0;
        double previousFilteredValueOfSampleAmplitude = 0.0;
        double peakValue = DBOFFSET;

        for(int i = 0; i < bufferSize; i++) {
            double absoluteValueOfSampleAmplitude = abs(buffer[i]);
            currentFilteredValueOfSampleAmplitude = LOWPASSFILTERTIMESLICE * absoluteValueOfSampleAmplitude + (1.0 - LOWPASSFILTERTIMESLICE) * previousFilteredValueOfSampleAmplitude;
            previousFilteredValueOfSampleAmplitude = currentFilteredValueOfSampleAmplitude;
            double amplitudeToConvertToDB = currentFilteredValueOfSampleAmplitude;
            double sampleDB = 20.0*log10(amplitudeToConvertToDB) + DBOFFSET;

            if(sampleDB == sampleDB && sampleDB != -Double.MAX_VALUE) {
                if(sampleDB > peakValue)
                    peakValue = sampleDB;
                decibels = peakValue;
            }
        }
        return decibels;
    }
//    Float32 calculateDecibelLevel(short *frames, UInt32 nframes) {
//        // These values should be in a more conventional location
//        //for a bunch of preprocessor defines in your real code
//#define DBOFFSET -74.0
//        // DBOFFSET is An offset that will be used to normalize
//        // the decibels to a maximum of zero.
//        // This is an estimate, you can do your own or construct
//        // an experiment to find the right value
//#define LOWPASSFILTERTIMESLICE .001
//        // LOWPASSFILTERTIMESLICE is part of the low pass filter
//        // and should be a small positive value
//
//        //    SInt16* samples = (SInt16*)(dev->_buflist.mBuffers[0].mData); // Step 1: get an array of
//        SInt16* samples = frames; // Step 1: get an array of
//        // your samples that you can loop through. Each sample contains the amplitude.
//
//        Float32 decibels = DBOFFSET; // When we have no signal we'll leave this on the lowest setting
//        Float32 currentFilteredValueOfSampleAmplitude, previousFilteredValueOfSampleAmplitude; // We'll need
//        // these in the low-pass filter
//
//        Float32 peakValue = DBOFFSET; // We'll end up storing the peak value here
//
//        for (int i=0; i < nframes; i++) {
//
//            Float32 absoluteValueOfSampleAmplitude = abs(samples[i]); //Step 2: for each sample,
//            // get its amplitude's absolute value.
//
//            // Step 3: for each sample's absolute value, run it through a simple low-pass filter
//            // Begin low-pass filter
//            currentFilteredValueOfSampleAmplitude = LOWPASSFILTERTIMESLICE * absoluteValueOfSampleAmplitude + (1.0 - LOWPASSFILTERTIMESLICE) * previousFilteredValueOfSampleAmplitude;
//            previousFilteredValueOfSampleAmplitude = currentFilteredValueOfSampleAmplitude;
//            Float32 amplitudeToConvertToDB = currentFilteredValueOfSampleAmplitude;
//            // End low-pass filter
//
//            Float32 sampleDB = 20.0*log10(amplitudeToConvertToDB) + DBOFFSET;
//            // Step 4: for each sample's filtered absolute value, convert it into decibels
//            // Step 5: for each sample's filtered absolute value in decibels,
//            // add an offset value that normalizes the clipping point of the device to zero.
//
//            if((sampleDB == sampleDB) && (sampleDB != -Double.MAX_VALUE)) { // if it's a rational number and
//                // isn't infinite
//
//                if(sampleDB > peakValue) peakValue = sampleDB; // Step 6: keep the highest value
//                // you find.
//                decibels = peakValue; // final value
//            }
//        }
//        return decibels;
//    }

}
