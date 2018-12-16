package cz.fit.metacentrum.service.action.submit.executor

import cz.fit.metacentrum.KotlinMockito
import cz.fit.metacentrum.config.FileNames
import cz.fit.metacentrum.domain.template.TemplateDataMatlab
import cz.fit.metacentrum.service.TemplateService
import cz.fit.metacentrum.service.TestData
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension

/**
 * @author Jakub Tucek
 */
@ExtendWith(MockitoExtension::class)
internal class MatlabScriptsExecutorTest {

    @InjectMocks
    private lateinit var ex: MatlabScriptsExecutor
    @Mock
    private lateinit var templateService: TemplateService
    @Spy
    private var templateDataBuilder = MatlabTemplateDataBuilder()

    @Test
    fun testMatlabScriptGeneratedFile() {
        ex.execute(TestData.metadata)

        Mockito.verify(templateService)
                .write(
                        KotlinMockito.eq("templates/matlab.mustache"),
                        KotlinMockito.eq(TestData.metadata.paths.storagePath!!.resolve("0/${FileNames.innerScript}")),
                        KotlinMockito.isA(TemplateDataMatlab::class.java)
                )
    }
}