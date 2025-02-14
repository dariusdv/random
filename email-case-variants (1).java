import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashSet;

public class EmailCaseVariantGenerator {
    
    public static List<String> generateEmailCaseVariants(String email) {
        // Using LinkedHashSet to maintain order and ensure uniqueness
        LinkedHashSet<String> variants = new LinkedHashSet<>();
        
        if (email == null || email.trim().isEmpty()) {
            return new ArrayList<>();
        }

        // Add the original input email first
        variants.add(email);

        // Split email into local part and domain
        String[] parts = email.split("@");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid email format");
        }

        String localPart = parts[0];
        String domain = parts[1];

        // Generate variants for the local part
        List<String> localPartVariants = generateLocalPartVariants(localPart);

        // Combine each local part variant with the domain
        for (String variant : localPartVariants) {
            variants.add(variant + "@" + domain);
        }

        // Convert set back to list to maintain the required return type
        return new ArrayList<>(variants);
    }

    private static List<String> generateLocalPartVariants(String localPart) {
        List<String> variants = new ArrayList<>();
        
        // Split the local part into segments based on dots, hyphens, and spaces
        String[] segments = localPart.split("(?=[.\\- ])|(?<=[.\\- ])");
        
        // 1. All lowercase variant
        variants.add(localPart.toLowerCase());
        
        // 2. All uppercase variant
        variants.add(localPart.toUpperCase());
        
        // 3. Title case variant (Each Word Capitalized)
        StringBuilder titleCaseBuilder = new StringBuilder();
        boolean newWord = true;
        
        for (String segment : segments) {
            if (segment.matches("[.\\- ]")) {
                titleCaseBuilder.append(segment);
                newWord = true;
            } else if (newWord) {
                titleCaseBuilder.append(segment.substring(0, 1).toUpperCase())
                              .append(segment.substring(1).toLowerCase());
                newWord = false;
            } else {
                titleCaseBuilder.append(segment.toLowerCase());
            }
        }
        variants.add(titleCaseBuilder.toString());

        return variants;
    }
}
