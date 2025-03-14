# Domain Shared Library

Shared domain components library for Java projects based on Domain-Driven Design (DDD).

## Description

This library provides basic implementations of DDD concepts, such as:

- Entities
- Value Objects
- Aggregates
- Domain Events
- Pagination utilities

## Requirements

- Java 21
- Maven 3.8+

## Usage

To use this library in your project, add the following dependency to your `pom.xml` file:

```xml
<dependency>
    <groupId>com.codingbetter</groupId>
    <artifactId>domain-shared-lib</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

And add the JFrog Artifactory repository:

```xml
<repositories>
    <repository>
        <id>jfrog-snapshots</id>
        <name>JFrog Artifactory Snapshots</name>
        <url>https://[your-jfrog-server]/artifactory/libs-snapshot</url>
    </repository>
</repositories>
```

## Main Components

### Domain Events

- `DomainEvent`: Interface for domain events
- `DomainEventPublisher`: Interface for domain event publishing

### Domain Models

- `Entity`: Interface for entities
- `ValueObject`: Interface for value objects
- `Identity`: Interface for identifiers
- `AggregateRoot`: Interface for aggregates
- `AbstractAggregateRoot`: Abstract implementation of aggregates

### Pagination

The library provides a `Page` interface and a `PageImpl` implementation for result pagination:

```java
public interface Page<T> {
    List<T> getContent();
    long getTotalElements();
    int getTotalPages();
    int getNumber();
    int getSize();
    boolean hasContent();
    boolean isFirst();
    boolean isLast();
    boolean hasNext();
    boolean hasPrevious();
    <U> Page<U> map(Function<? super T, ? extends U> converter);
}
```

The `PageImpl` class should not be directly instantiated by consumer projects. Instead, use the `PageUtils` class to create `Page` instances:

```java
// Create an empty page
Page<String> emptyPage = PageUtils.empty();

// Create a page from a list
List<String> items = Arrays.asList("Item 1", "Item 2", "Item 3");
Page<String> page = PageUtils.of(items, 10, 0, 3);

// Paginate a list
List<String> allItems = Arrays.asList("Item 1", "Item 2", "Item 3", "Item 4", "Item 5");
Page<String> paginatedItems = PageUtils.paginate(allItems, 0, 2); // First page with 2 items
```

#### Mapping Between Architectural Layers

One of the most powerful features of the pagination utilities is the ability to map between different architectural layers. This allows you to maintain proper separation of concerns while still leveraging the pagination functionality throughout your application.

##### Infrastructure to Domain Layer Mapping

When retrieving data from a database or external service in the infrastructure layer, you can map it to domain objects:

```java
// Infrastructure layer - Database entities
Page<ProductEntity> productEntitiesPage = productRepository.findAll(pageable);

// Map to domain objects
Page<Product> productDomainPage = PageUtils.map(productEntitiesPage, entity -> {
    return new Product(
        new ProductId(entity.getId()),
        entity.getName(),
        entity.getDescription(),
        Money.of(entity.getPrice(), entity.getCurrency())
    );
});

// Now you can work with domain objects in your domain layer
```

##### Domain to Application Layer Mapping

Similarly, when exposing domain objects to the application layer (e.g., REST controllers), you can map them to DTOs:

```java
// Domain layer - Domain objects
Page<Product> productDomainPage = productService.findProducts(criteria);

// Map to DTOs for the application layer
Page<ProductDTO> productDtoPage = PageUtils.map(productDomainPage, product -> {
    return new ProductDTO(
        product.getId().getValue(),
        product.getName(),
        product.getDescription(),
        product.getPrice().getAmount(),
        product.getPrice().getCurrency()
    );
});

// Return DTOs to the client
return ResponseEntity.ok(productDtoPage);
```

This approach ensures that:
1. Each layer works with its own appropriate object types
2. Pagination metadata (total elements, page numbers, etc.) is preserved across layers
3. The domain layer remains isolated from infrastructure and presentation concerns

## Tests

The library includes unit tests for all main components. To run the tests:

```bash
mvn test
```

The tests cover:

- Domain events
- Aggregates
- Pagination

## Publishing to JFrog Artifactory

To publish this library to JFrog Artifactory, configure your credentials in the `~/.m2/settings.xml` file:

```xml
<settings>
    <servers>
        <server>
            <id>jfrog-snapshots</id>
            <username>your-username</username>
            <password>your-password</password>
        </server>
        <server>
            <id>jfrog-releases</id>
            <username>your-username</username>
            <password>your-password</password>
        </server>
    </servers>
</settings>
```

And run the command:

```bash
mvn clean deploy
```

## Components

### Domain Events and Aggregates

The library provides a `DomainEvent` interface that defines the basic structure for domain events:

```java
public interface DomainEvent extends Serializable {
    UUID getId();
    LocalDateTime getOccurredOn();
}
```

#### Creating and Publishing Domain Events

Domain events are typically created and published by aggregates. The `AbstractAggregateRoot` class provides the necessary infrastructure to manage domain events:

```java
// 1. Define your domain event
public class ProductCreatedEvent implements DomainEvent {
    private static final long serialVersionUID = 1L;
    private final UUID id;
    private final LocalDateTime occurredOn;
    private final String productId;
    private final String name;
    
    public ProductCreatedEvent(String productId, String name) {
        this.id = UUID.randomUUID();
        this.occurredOn = LocalDateTime.now();
        this.productId = productId;
        this.name = name;
    }
    
    @Override
    public UUID getId() {
        return id;
    }
    
    @Override
    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }
    
    public String getProductId() {
        return productId;
    }
    
    public String getName() {
        return name;
    }
}

// 2. Create your aggregate that extends AbstractAggregateRoot
public class Product extends AbstractAggregateRoot {
    private final ProductId id;
    private String name;
    private String description;
    private Money price;
    
    // Constructor for creating a new product
    public static Product create(ProductId id, String name, String description, Money price) {
        Product product = new Product(id, name, description, price);
        
        // Register the domain event
        product.addDomainEvent(new ProductCreatedEvent(id.getValue(), name));
        
        return product;
    }
    
    // Private constructor
    private Product(ProductId id, String name, String description, Money price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }
    
    // Method to update the product price
    public void updatePrice(Money newPrice) {
        if (this.price.equals(newPrice)) {
            return; // No change, no event
        }
        
        this.price = newPrice;
        
        // Register a price updated event
        this.addDomainEvent(new ProductPriceUpdatedEvent(
            this.id.getValue(), 
            newPrice.getAmount(), 
            newPrice.getCurrency()
        ));
    }
    
    // Getters
    public ProductId getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Money getPrice() {
        return price;
    }
}

// 3. Implement a domain service or application service to handle the aggregate and publish events
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final DomainEventPublisher eventPublisher;
    
    public ProductService(ProductRepository productRepository, DomainEventPublisher eventPublisher) {
        this.productRepository = productRepository;
        this.eventPublisher = eventPublisher;
    }
    
    @Transactional
    public Product createProduct(String name, String description, BigDecimal amount, String currency) {
        // Create a new product
        ProductId id = new ProductId(UUID.randomUUID().toString());
        Money price = Money.of(amount, currency);
        
        Product product = Product.create(id, name, description, price);
        
        // Save the product
        productRepository.save(product);
        
        // Publish all domain events
        publishEventsFrom(product);
        
        return product;
    }
    
    @Transactional
    public Product updateProductPrice(String productId, BigDecimal newAmount, String currency) {
        // Find the product
        Product product = productRepository.findById(new ProductId(productId))
            .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        
        // Update the price
        product.updatePrice(Money.of(newAmount, currency));
        
        // Save the product
        productRepository.save(product);
        
        // Publish all domain events
        publishEventsFrom(product);
        
        return product;
    }
    
    // Helper method to publish events from an aggregate
    private void publishEventsFrom(AbstractAggregateRoot aggregate) {
        List<DomainEvent> events = aggregate.getDomainEvents();
        events.forEach(eventPublisher::publish);
        aggregate.clearDomainEvents();
    }
}

// 4. Implement the DomainEventPublisher interface
@Component
public class RabbitMQDomainEventPublisher implements DomainEventPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;
    
    public RabbitMQDomainEventPublisher(RabbitTemplate rabbitTemplate, 
                                        @Value("${rabbitmq.exchange.domain-events}") String exchangeName) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
    }
    
    @Override
    public void publish(DomainEvent event) {
        String routingKey = determineRoutingKey(event);
        rabbitTemplate.convertAndSend(exchangeName, routingKey, event);
    }
    
    private String determineRoutingKey(DomainEvent event) {
        // Simple implementation - use the event class name as routing key
        return event.getClass().getSimpleName().toLowerCase().replace("event", "");
    }
}
```

#### Event Flow

1. **Event Creation**: Domain events are created within the aggregate when state changes occur. For example, when a new `Product` is created or when its price is updated.

2. **Event Registration**: The aggregate registers these events using the `addDomainEvent` method inherited from `AbstractAggregateRoot`.

3. **Event Collection**: After the aggregate is saved, the service collects all registered events using `getDomainEvents()`.

4. **Event Publication**: Each event is published through the `DomainEventPublisher` implementation.

5. **Event Clearing**: After publication, events are cleared from the aggregate using `clearDomainEvents()` to prevent duplicate publications.

This pattern ensures that domain events are only published after the aggregate's state changes have been successfully persisted, maintaining transactional consistency.

## License

This project is licensed under the MIT License - see the LICENSE file for details.