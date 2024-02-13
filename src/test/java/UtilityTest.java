import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.cft.util.Utility;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class UtilityTest {
    private Utility utility;
    private final String RESOURCE_DIR = "src/test/resources";
    private final String OUTPUT_DIR = RESOURCE_DIR + "/outputFiles";

    @BeforeEach
    void init() {
        utility = new Utility();
    }

    @Test
    void testCommandExecutionWithoutAppending() {
        List<String> command = prepareCommand(false);

        utility.setInput(command);
        utility.executeCommand();

        assertFilesExistAndContentIsCorrect(false);
    }

    @Test
    void testCommandExecutionWithAppending() {
        List<String> command = prepareCommand(true);

        utility.setInput(command);
        utility.executeCommand();

        assertFilesExistAndContentIsCorrect(true);

    }

    private List<String> prepareCommand(boolean appendMode) {
        String stats = "-f ";
        String prefix = "-p prefix_ ";
        String outputDir = "-o " + OUTPUT_DIR;
        String INPUT_DIR = RESOURCE_DIR + "/inputFiles";
        String inputFiles = " " + INPUT_DIR + "/file1.txt " + INPUT_DIR + "/file2.txt " + INPUT_DIR + "/file3.txt";
        String appendFlag = appendMode ? " -a" : "";

        return List.of((stats + prefix + outputDir + appendFlag + inputFiles).split(" "));
    }

    private void assertFilesExistAndContentIsCorrect(boolean appendingMode) {
        File integers = new File(OUTPUT_DIR + "/prefix_integers.txt");
        File floats = new File(OUTPUT_DIR + "/prefix_floats.txt");
        File strings = new File(OUTPUT_DIR + "/prefix_strings.txt");

        assertTrue(integers.exists() && floats.exists() && strings.exists());

        String integersString = appendingMode ? """
                123
                567
                890
                123
                567
                890
                """ : "123\n567\n890\n";
        String floatsString = appendingMode ? """
                1.23
                5.67
                8.9
                1.23
                5.67
                8.9
                """ : "1.23\n5.67\n8.9\n";
        String stringsString = appendingMode ? """
                Строка 1
                Строка 2
                Строка 3
                Строка 1
                Строка 2
                Строка 3
                """ : "Строка 1\nСтрока 2\nСтрока 3\n";
        byte[] russianBytes = stringsString.getBytes();
        stringsString = new String(russianBytes, StandardCharsets.UTF_8);
        assertFileContentCorrect(integers, integersString);
        assertFileContentCorrect(floats, floatsString);
        assertFileContentCorrect(strings, stringsString);
    }

    private void assertFileContentCorrect(File file, String expectedContent) {
        try {
            Scanner scanner = new Scanner(file); // Указываем UTF-8 как кодировку
            String actualContent = scanner.useDelimiter("\\A").next();
            assertEquals(expectedContent, actualContent);
        } catch (IOException e) {
            fail("File not found: " + e.getMessage());
        }
    }


}
