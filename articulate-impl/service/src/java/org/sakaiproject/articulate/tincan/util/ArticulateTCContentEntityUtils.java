package org.sakaiproject.articulate.tincan.util;

import lombok.Setter;

import org.sakaiproject.content.api.ContentCollectionEdit;
import org.sakaiproject.content.api.ContentHostingService;
import org.sakaiproject.content.api.ContentResourceEdit;
import org.sakaiproject.entity.api.Edit;
import org.sakaiproject.entity.api.ResourcePropertiesEdit;
import org.sakaiproject.exception.IdUsedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Robert Long (rlong @ unicon.net)
 */
public class ArticulateTCContentEntityUtils {

    private final Logger log = LoggerFactory.getLogger(ArticulateTCContentEntityUtils.class);

    @Setter
    private ContentHostingService contentHostingService;

    public ContentCollectionEdit addOrEditCollection(String collectionPath) {
        try {
            return addCollection(collectionPath);
        } catch (IdUsedException iue) {
            // id exists, edit the collection instead
            log.debug("Collection exists. Getting the EDIT collection object instead for path: {}", collectionPath);
            return editCollection(collectionPath);
        } catch (Exception e) {
            log.error("Error getting the add collection object.", e);
        }

        return null;
    }

    public ContentCollectionEdit addCollection(String collectionPath) throws Exception {
        return contentHostingService.addCollection(collectionPath);
    }

    public ContentCollectionEdit editCollection(String collectionPath) {
        try {
            return contentHostingService.editCollection(collectionPath);
        } catch (Exception e) {
            log.error("Error getting the edit collection object.", e);
        }

        return null;
    }

    public ContentResourceEdit addOrEditResource(String resourcePath) {
        try {
            return addResource(resourcePath);
        } catch (IdUsedException e) {
            // id exists, edit the resource instead
            log.debug("Resource exists. Getting the EDIT resource object instead for path: {}", resourcePath);
            return editResource(resourcePath);
        } catch (Exception e) {
            log.error("Error getting the add resource object.", e);
        }

        return null;
    }

    public ContentResourceEdit addResource(String resourcePath) throws Exception {
        return contentHostingService.addResource(resourcePath);
    }

    public ContentResourceEdit editResource(String resourcePath) {
        try {
            return contentHostingService.editResource(resourcePath);
        } catch (Exception e) {
            log.error("Error getting the edit resource object.", e);
        }

        return null;
    }

    public Edit addProperties(Edit editEntity, String[] keys, String[] values) {
        ResourcePropertiesEdit props = editEntity.getPropertiesEdit();
        for (int i = 0; i < keys.length; i++) {
            props.addProperty(keys[i], values[i]);
        }

        return editEntity;
    }

}
