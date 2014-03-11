package sapic.entities

import org.joda.time.DateTime

// implements http://sandbox.iii.com/docs/#swagger/interactive.html%3FTocPath%3D_____1
class Item {

    Long id
    DateTime updatedDate
    protected void setUpdatedDate(String updatedDate) { this.updatedDate = new DateTime(updatedDate) }
    DateTime createdDate
    protected void setCreatedDate(String createdDate) { this.createdDate = new DateTime(createdDate) }
    DateTime deletedDate
    protected void setDeletedDate(String deletedDate) { this.deletedDate = new DateTime(deletedDate) }
    List bibIds
    Object location         // todo: map this properly
    Object status           // todo: map this properly
    String barcode
    Boolean deleted
    String callNumber
    Object fixedFields      // todo: map this properly
    Object varFields        // todo: map this properly

}

