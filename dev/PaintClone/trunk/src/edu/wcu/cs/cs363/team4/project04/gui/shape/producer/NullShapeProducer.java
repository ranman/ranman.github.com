package edu.wcu.cs.cs363.team4.project04.gui.shape.producer;


/**
 * This class implements the Singleton Pattern and the
 * NullShapeProducer.
 * 
 * @author Joseph Randall Hunt
 * @version Nov 19, 2010
 */
public final class NullShapeProducer extends AbstractShapeProducer {

    private NullShapeProducer() {
        // do nothing
    }

    private static volatile NullShapeProducer nullShapeProducer;

    /**
     * This method returns the single instance of NullShapeProducer.
     * 
     * @return singleton instance of NullShapeProducer
     */
    public static NullShapeProducer getNullShapeProducer() {
        if (nullShapeProducer == null) {
            nullShapeProducer = new NullShapeProducer();
        }
        return nullShapeProducer;
    }
}
