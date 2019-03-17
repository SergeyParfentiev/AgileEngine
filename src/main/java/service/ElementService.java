package service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

public class ElementService {
    private Logger LOGGER = LoggerFactory.getLogger(ElementService.class);
    private String[] attributeArray = new String[]{"class", "href", "title"};
    String CHARSET_NAME = "utf8";

    public Optional<Element> getById(String filePath, String id) {
        return getByAttribute(filePath, id, null);
    }

    public Optional<Element> getSameElement(String filePath, Element originalElement) {
        return getByAttribute(filePath, null, originalElement);
    }

    private Optional<Element> getByAttribute(String filePath, String attribute, Element originalElement) {
        File file = null;
        try {
            file = new File(filePath);
            Document document = Jsoup.parse(file, CHARSET_NAME, file.getAbsolutePath());
            if (originalElement == null) {
                return Optional.of(document.getElementById(attribute));
            } else {
                return getSameElementByAttributes(document, originalElement);
            }
        } catch (IOException e) {
            LOGGER.error("Error reading [{}] file", file.getAbsolutePath(), e);
            return Optional.empty();
        }
    }

    private Optional<Element> getSameElementByAttributes(Document document, Element originalElement) {
        for (int i = 0; i < attributeArray.length; i++) {
            Elements elements = document.getElementsByAttributeValueContaining(attributeArray[i], originalElement.attr(attributeArray[i]));
            if (elements.size() > 0) {
                Iterator iterator = elements.iterator();
                while (iterator.hasNext()) {
                    Element element = (Element) iterator.next();
                    for (int j = 0; j < attributeArray.length; ) {
                        if (!element.attr(attributeArray[i]).contains(originalElement.attr(attributeArray[i]))) {
                            iterator.remove();
                            break;
                        }
                        if (j++ == i) {
                            j++;
                        }
                    }
                    if (elements.size() == 1) {
                        return Optional.of(elements.first());
                    }
                }
            }
        }

        return Optional.empty();
    }
}
