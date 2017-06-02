package gr.teiath.teiathservices;

import java.util.Date;

public class product {
    private String code;
    private String description;
    private float price;
    private String productCategoryName;
    private String productCategory;
    private String productName;
    private String productStatusName;
    private String productStatus;
    private String currency;
    private String ownerName;
    private String ownerRating;
    private String ownerComments;
    private String comments;
    private String transactionTypeName;
    private String transactionType;
    private String productBrand;
    private String sendHome;
    private String enabled;
    private Date listingCreationDate;
    private Date purchaseDate;

    public product(String code, String description, float price, String productCategoryName, String productCategory, String productName, String productStatusName, String productStatus, String currency, String ownerName, String ownerRating, String ownerComments, String comments, String transactionTypeName, String transactionType, String productBrand, String sendHome, String enabled, Date listingCreationDate, Date purchaseDate) {
        this.code = code;
        this.description = description;
        this.price = price;
        this.productCategoryName = productCategoryName;
        this.productCategory = productCategory;
        this.productName = productName;
        this.productStatusName = productStatusName;
        this.productStatus = productStatus;
        this.currency = currency;
        this.ownerName = ownerName;
        this.ownerRating = ownerRating;
        this.ownerComments = ownerComments;
        this.comments = comments;
        this.transactionTypeName = transactionTypeName;
        this.transactionType = transactionType;
        this.productBrand = productBrand;
        this.sendHome = sendHome;
        this.enabled = enabled;
        this.listingCreationDate = listingCreationDate;
        this.purchaseDate = purchaseDate;
    }
}
