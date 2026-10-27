package fr.freshperf.pve4j.entities.nodes.node.qemu;

import fr.freshperf.pve4j.request.ProxmoxHttpClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("PveQemuVmVnc Tests")
class PveQemuVmVncTest {

    @Test
    @DisplayName("Should request a websocket-ready VNC proxy when asked")
    void shouldRequestWebsocketReadyProxy() throws Exception {
        ProxmoxHttpClient client = mock(ProxmoxHttpClient.class);
        ProxmoxHttpClient.RequestBuilder builder = mock(ProxmoxHttpClient.RequestBuilder.class, RETURNS_SELF);
        when(client.post("nodes/node1/qemu/100/vncproxy")).thenReturn(builder);
        PveQemuVmVncProxy proxy = new PveQemuVmVncProxy();
        doReturn(proxy).when(builder).execute(PveQemuVmVncProxy.class);

        PveQemuVmVncProxy result = new PveQemuVmVnc(client, "node1", 100).getVncProxy(true).execute();

        assertThat(result).isSameAs(proxy);
        verify(builder).param("websocket", "1");
    }
}
