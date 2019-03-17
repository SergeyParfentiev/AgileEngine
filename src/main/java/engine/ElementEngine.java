package engine;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ElementService;

import java.util.Optional;

public class ElementEngine {
    private Logger LOGGER = LoggerFactory.getLogger(ElementEngine.class);
    private String originalFilePath;
    private String otherSampleFilePath;
    private ElementService elementService;

    public ElementEngine(String originalFilePath, String otherSampleFilePath) {
        this.originalFilePath = originalFilePath;
        this.otherSampleFilePath = otherSampleFilePath;
        elementService = new ElementService();
    }

    public void run() {
        String ORIGINAL_ID_OF_ELEMENT = "make-everything-ok-button";
        Optional<Element> originalButton = elementService.getById(originalFilePath, ORIGINAL_ID_OF_ELEMENT);
        if (originalButton.isPresent()) {
            Optional<Element> otherButton = elementService.getSameElement(otherSampleFilePath, originalButton.get());
            if (otherButton.isPresent()) {

                Elements parentList = otherButton.get().parents();
                Element childElement = originalButton.get();
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = parentList.size() - 1; i >= 0; i--) {
                    stringBuilder.append(parentList.get(i).nodeName()).append(" > ");
                }
                stringBuilder.append(childElement.nodeName());
                LOGGER.info("Same button path: [{}]", stringBuilder);
            } else {
                LOGGER.error("There is no same button by id: [{}]", ORIGINAL_ID_OF_ELEMENT);
            }

            System.out.println();

        } else {
            LOGGER.error("There is no original button by id: [{}]", ORIGINAL_ID_OF_ELEMENT);
        }
    }
}
