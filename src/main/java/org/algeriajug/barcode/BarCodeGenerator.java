package org.algeriajug.barcode;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.krysalis.barcode4j.BarcodeException;
import org.krysalis.barcode4j.BarcodeGenerator;
import org.krysalis.barcode4j.BarcodeUtil;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class BarCodeGenerator {
    private static final Logger logger = LoggerFactory.getLogger(BarCodeGenerator.class);
    private static DefaultConfigurationBuilder builder;
    private static Configuration configuration;
    private static BarcodeGenerator barcodeGenerator;
    public BufferedImage generateBarCodeAsBufferedImage(String code) {
        try {
            builder = new DefaultConfigurationBuilder();
            configuration = builder.build(this.getClass().getClassLoader().getResourceAsStream("conf.xml"));
        } catch (SAXException | IOException | ConfigurationException e) {
            logger.error(e.getMessage());
        }
        try {
            barcodeGenerator = BarcodeUtil.getInstance().createBarcodeGenerator(configuration);
        } catch (ConfigurationException | BarcodeException e) {
            logger.error(e.getMessage());
        }
        BitmapCanvasProvider provider = new BitmapCanvasProvider(300, BufferedImage.TYPE_BYTE_GRAY, true, 0);
        barcodeGenerator.generateBarcode(provider, code);
        return provider.getBufferedImage();
    }
}
