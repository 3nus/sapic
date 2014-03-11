package sapic.entities

import net.sf.json.JSON
import org.joda.time.DateTime

// implements http://sandbox.iii.com/docs/#swagger/interactive.html%3FTocPath%3D_____1
class Bib {

    Long id
    DateTime updatedDate
    protected void setUpdatedDate(String updatedDate) { this.updatedDate = new DateTime(updatedDate) }
    DateTime createdDate
    protected void setCreatedDate(String createdDate) { this.createdDate = new DateTime(createdDate) }
    DateTime deletedDate
    protected void setDeletedDate(String deletedDate) { this.deletedDate = new DateTime(deletedDate) }
    Boolean deleted
    Boolean suppressed
    String lang
    String title
    String author
    Object materialType     // todo: map this properly
    Object bibLevel         // todo: map this properly
    Integer publishYear
    DateTime catalogDate
    protected void setCatalogDate(String catalogDate) { this.catalogDate = new DateTime(catalogDate) }
    String country
    Object orderInfo        // todo: map this properly
    Object fixedFields      // todo: map this properly
    Object varFields        // todo: map this properly
    JSON marc


    def delete() {

    }
}

