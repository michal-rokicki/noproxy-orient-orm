package pl.megaoak.orientorm.accessor;

import com.orientechnologies.orient.core.id.ORecordId;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.megaoak.orientorm.model.FieldDemo;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
public class GenericFinderTest {

    @Test
    public void shouldEqual() throws NoSuchMethodException {
        Method m = GenericFinderTest.class.getMethod("getSomeGenerics");
        GenericClass first = GenericFinder.find(m.getGenericReturnType());
        GenericClass second = GenericFinder.find(m.getGenericReturnType());

        assertTrue(first.equals(second));
    }

    @Test
    public void shouldFindComplexGenericTree() throws NoSuchMethodException {
        Method m = GenericFinderTest.class.getMethod("getSomeGenerics");
        GenericClass result = GenericFinder.find(m.getGenericReturnType());

        assertEquals(result.cls, List.class);
        assertEquals(result.generics.length, 1);

        assertEquals(result.generics[0].cls, Map.class);
        assertEquals(result.generics[0].generics.length, 2);

        assertEquals(result.generics[0].generics[0].cls, String.class);
        assertEquals(result.generics[0].generics[0].generics.length, 0);
        assertEquals(result.generics[0].generics[1].cls, Map.class);
        assertEquals(result.generics[0].generics[1].generics.length, 2);

        assertEquals(result.generics[0].generics[1].generics[0].cls, Long.class);
        assertEquals(result.generics[0].generics[1].generics[0].generics.length, 0);
        assertEquals(result.generics[0].generics[1].generics[1].cls, Set.class);
        assertEquals(result.generics[0].generics[1].generics[1].generics.length, 1);

        assertEquals(result.generics[0].generics[1].generics[1].cls, Set.class);
        assertEquals(result.generics[0].generics[1].generics[1].generics.length, 1);

        assertEquals(result.generics[0].generics[1].generics[1].generics[0].cls, String.class);
        assertEquals(result.generics[0].generics[1].generics[1].generics[0].generics.length, 0);

        assertEquals(result.classesInChain.size(), 5);
        assertTrue(result.classesInChain.containsAll(asList(List.class, String.class, Map.class, Set.class, Long.class)));

        assertEquals(result.generics[0].classesInChain.size(), 4);
        assertTrue(result.generics[0].classesInChain.containsAll(asList(String.class, Map.class, Set.class, Long.class)));

        assertEquals(result.generics[0].generics[0].classesInChain.size(), 1);
        assertTrue(result.classesInChain.containsAll(asList(String.class)));

        assertEquals(result.generics[0].generics[1].classesInChain.size(), 4);
        assertTrue(result.generics[0].generics[1].classesInChain.containsAll(
                asList(String.class, Map.class, Set.class, Long.class)));

        assertEquals(result.generics[0].generics[1].generics[0].classesInChain.size(), 1);
        assertTrue(result.generics[0].generics[1].generics[0].classesInChain.containsAll(asList(Long.class)));

        assertEquals(result.generics[0].generics[1].generics[1].classesInChain.size(), 2);
        assertTrue(result.generics[0].generics[1].generics[1].classesInChain.containsAll(asList(String.class, Set.class)));

        assertEquals(result.generics[0].generics[1].generics[1].generics[0].classesInChain.size(), 1);
        assertTrue(result.generics[0].generics[1].generics[1].generics[0].classesInChain.containsAll(asList(String.class)));
    }

    @Test
    public void shouldFindNoGenerics() throws NoSuchMethodException {
        Method m = GenericFinderTest.class.getMethod("getList");
        GenericClass result = GenericFinder.find(m.getGenericReturnType());

        assertEquals(result.cls, List.class);
        assertEquals(result.generics.length, 0);
    }

    @Test
    public void shouldFindNoGenerics2() throws NoSuchMethodException {
        Method m = GenericFinderTest.class.getMethod("getInt");
        GenericClass result = GenericFinder.find(m.getGenericReturnType());

        assertEquals(result.cls, int.class);
        assertEquals(result.generics.length, 0);
    }

    @Test
    public void shouldFindNoGenerics3() throws NoSuchMethodException {
        Method m = GenericFinderTest.class.getMethod("getString");
        GenericClass result = GenericFinder.find(m.getGenericReturnType());

        assertEquals(result.cls, String.class);
        assertEquals(result.generics.length, 0);
    }

    public List getList() {
        return null;
    }

    public int getInt() {
        return 0;
    }

    public String getString() {
        return "";
    }

    public List<Map<String,Map<Long,Set<String>>>> getSomeGenerics() {
        return null;
    }
}
