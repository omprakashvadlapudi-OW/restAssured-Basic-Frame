# RestAssured API Testing Framework

A **generic, reusable** REST API testing framework built with RestAssured, TestNG, and Allure reporting. This framework is designed to work with any API project through configuration and data-driven testing.

## Features

### Core Capabilities
- ✅ **All HTTP Methods**: GET, POST, PUT, PATCH, DELETE
- ✅ **Data-Driven Testing**: CSV-based test data with caching
- ✅ **Dynamic Values**: Extensible plugin architecture for runtime value generation
- ✅ **Template Engine**: JSON request templates with placeholder substitution
- ✅ **Enhanced Validation**: Comprehensive assertion methods
- ✅ **Allure Reporting**: Detailed test reports with request/response logging
- ✅ **Environment Support**: Multiple environment configurations (dev, qa, prod)
- ✅ **Thread-Safe**: All utilities designed for parallel execution

### Recent Improvements (v2.0)

#### Phase 1: Critical Bug Fixes
- Fixed DELETE method logging bug in `APIClient`
- Fixed header usage bug in `EmployeeAPI`
- Improved exception handling in `CsvUtils`
- Removed hardcoded paths in `ConfigManager`
- Aligned Jackson dependency versions

#### Phase 2: Algorithm Enhancements
- **CsvUtils**: Added caching mechanism for better performance
- **DynamicValueProcessor**: Plugin architecture for custom functions
- **FileUtils**: Proper resource management with NIO
- **DataGenerator**: Thread-safe using ThreadLocalRandom
- **ConfigManager**: Environment-based configuration loading
- **TemplateEngine**: Clean separation of concerns

## Project Structure

```
RestAssured_Urbuddi/
├── src/main/java/
│   ├── api/                    # API layer (project-specific)
│   │   ├── AuthAPI.java
│   │   └── EmployeeAPI.java
│   ├── base/                   # Core framework classes
│   │   ├── APIClient.java      # HTTP client with all methods
│   │   ├── BaseTest.java       # Base test class
│   │   ├── HeaderBuilder.java  # Header utilities
│   │   └── Validator.java      # Enhanced assertions
│   ├── config/                 # Configuration management
│   │   └── ConfigManager.java  # Environment-aware config loader
│   ├── dataProvider/           # TestNG data providers
│   │   ├── EmployeeDP.java     # Example: Employee-specific
│   │   └── GenericDataProvider.java  # Generic CSV provider
│   ├── reportManager/          # Reporting
│   │   └── AllureManager.java
│   └── utils/                  # Utilities
│       ├── CsvUtils.java       # CSV reading with caching
│       ├── DataGenerator.java  # Thread-safe data generation
│       ├── DynamicValueProcessor.java  # Dynamic value resolver
│       ├── FileUtils.java      # File operations
│       ├── RequestProcess.java # Request processing
│       ├── ResponseProcess.java # Response parsing
│       ├── TemplateEngine.java # Template processing
│       └── TestDataMerger.java # Data merging
├── src/test/java/
│   ├── dataProvider/
│   └── tests/
│       └── AddEmployeeTest.java
└── src/test/resources/
    ├── data/
    │   ├── initial/            # Base data files
    │   └── tests/              # Test data CSVs
    ├── properties/
    │   ├── base.properties     # Base configuration
    │   ├── dev.properties      # Dev environment (optional)
    │   └── qa.properties       # QA environment (optional)
    ├── request/                # JSON request templates
    └── schemas/                # JSON schemas for validation
```

## Quick Start

### 1. Configuration

Create `src/test/resources/properties/base.properties`:

```properties
base.url=https://api.example.com
header.contentType=application/json
header.tenant.id=T001
header.accept=*/*

url.login=/v1/authentication
url.users=/v1/users

login.request=loginRequest.json
request.folder=src/test/resources/request/
testCsv.folder=src/test/resources/data/tests
```

### 2. Create API Class

```java
public class UserAPI extends APIClient {
    private String tenantId = ConfigManager.get("header.tenant.id");
    
    public Response createUser(String url, String token, String payload) {
        return post(url, payload, HeaderBuilder.tokenHeaders(token, tenantId));
    }
    
    public Response getUser(String url, String token, String userId) {
        return get(url + "/" + userId, HeaderBuilder.tokenHeaders(token, tenantId));
    }
    
    public Response updateUser(String url, String token, String payload) {
        return put(url, payload, HeaderBuilder.tokenHeaders(token, tenantId));
    }
    
    public Response deleteUser(String url, String token, String userId) {
        return delete(url + "/" + userId, HeaderBuilder.tokenHeaders(token, tenantId));
    }
}
```

### 3. Create Test

```java
public class UserTest extends BaseTest {
    UserAPI userAPI = new UserAPI();
    
    @Test(dataProvider = "csvData", dataProviderClass = GenericDataProvider.class)
    public void testCreateUser(Map<String, String> row) {
        String request = TemplateEngine.process("createUserRequest.json", initialUserCsv, row);
        Response res = userAPI.createUser(userUrl, token, request);
        
        Validator.verifyStatusCode(res, 201);
        Validator.verifyField(res, "message", "success");
    }
}
```

### 4. Create Test Data CSV

`src/test/resources/data/tests/user_tests.csv`:

```csv
test_id,test_data,response
TC001,"name=John Doe
email=RANDOM_EMAIL()
age=RANDOM(18,65)",codeMessage=success
TC002,"name=Jane Smith
email=jane@test.com
age=30",codeMessage=success
```

### 5. Run Tests

```bash
mvn clean test
mvn allure:serve  # View Allure report
```

## Advanced Features

### Dynamic Value Functions

The framework supports dynamic value generation in test data:

```csv
name=User_UUID()
email=RANDOM_EMAIL(test.com)
phone=RANDOM(1000000000,9999999999)
date=TODAY()
timestamp=TIMESTAMP()
id=CONCAT(USER_,RANDOM(1,1000))
```

**Register Custom Functions:**

```java
DynamicValueProcessor.registerFunction("CUSTOM", args -> {
    // Your custom logic
    return "custom_value";
});
```

### Caching for Performance

```java
// Enable caching for frequently accessed files
Map<String, String> data = CsvUtils.readCsvAsMap("base_data.csv", true);
List<Map<String, String>> rows = CsvUtils.readCsvAsList("test_data.csv", true);

// Clear cache when needed
CsvUtils.clearCache("test_data.csv");
CsvUtils.clearAllCaches();
```

### Environment-Specific Configuration

Run tests with different environments:

```bash
mvn test -Denv=qa
mvn test -Denv=prod
```

The framework loads:
1. `base.properties` (common settings)
2. `{env}.properties` (environment-specific overrides)

### Enhanced Validations

```java
// Status code
Validator.verifyStatusCode(response, 200);

// Field values
Validator.verifyField(response, "user.name", "John");
Validator.verifyNotNull(response, "user.id");

// Response time
Validator.verifyResponseTime(response, 2000);

// Headers
Validator.verifyContentType(response, "application/json");
Validator.verifyHeader(response, "X-Request-ID", expectedId);

// Lists
Validator.verifyListSize(response, "users", 10);
Validator.verifyContains(response, "users.name", "John");

// Patterns
Validator.verifyPattern(response, "email", "^[\\w.-]+@[\\w.-]+\\.\\w+$");

// Body content
Validator.verifyBodyContains(response, "success");
```

## Data Generation

Thread-safe data generators:

```java
// Numbers
int num = DataGenerator.getRandomNumber(1, 100);
long id = DataGenerator.getRandomLong(1000L, 9999L);
double price = DataGenerator.getRandomDouble(10.0, 100.0);

// Strings
String str = DataGenerator.getRandomString(10);
String lower = DataGenerator.getRandomString(8, CharacterSet.LOWERCASE);
String upper = DataGenerator.getRandomString(8, CharacterSet.UPPERCASE);
String alphanum = DataGenerator.getRandomString(12, CharacterSet.ALPHANUMERIC);

// Email & Phone
String email = DataGenerator.getRandomEmail();
String customEmail = DataGenerator.getRandomEmail("company.com");
String phone = DataGenerator.getRandomPhoneNumber();

// Random selection
String[] colors = {"red", "blue", "green"};
String color = DataGenerator.getRandomElement(colors);
```

## Best Practices

1. **Use Generic Components**: Prefer `GenericDataProvider` over project-specific providers
2. **Enable Caching**: Use caching for base data files that don't change
3. **Environment Variables**: Use environment-specific configs for different test environments
4. **Dynamic Values**: Leverage dynamic value functions instead of hardcoded test data
5. **Template Reuse**: Create reusable JSON templates for common request patterns
6. **Validation**: Use specific validators instead of generic assertions

## Migration from v1.0

If you're upgrading from the previous version:

1. **Update imports**: Some classes have been enhanced
2. **Enable caching**: Add `true` parameter to CSV reading methods
3. **Use TemplateEngine**: Replace direct `RequestProcess` calls
4. **Update dynamic values**: New syntax supports parameters: `RANDOM(min,max)`
5. **Environment config**: Create environment-specific property files

## Dependencies

- RestAssured 5.5.6
- TestNG 7.11.0
- Allure 2.31.0
- Jackson 2.20.1
- OpenCSV 5.12.0

## Contributing

This framework is designed to be extended. Add custom:
- API classes for your endpoints
- Data providers for your test data formats
- Dynamic value functions for your use cases
- Validators for your specific assertions

## License

This framework is provided as-is for API testing purposes.

---

**Version**: 2.0  
**Last Updated**: December 2025  
**Maintained by**: Framework Team
