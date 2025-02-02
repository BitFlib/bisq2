/*
 * This file is part of Bisq.
 *
 * Bisq is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * Bisq is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Bisq. If not, see <http://www.gnu.org/licenses/>.
 */

package bisq.chat.channel;

import bisq.chat.message.ChatMessage;
import bisq.chat.trade.priv.PrivateTradeChannel;
import bisq.chat.trade.pub.PublicTradeChannel;
import bisq.common.observable.Observable;
import bisq.common.observable.ObservableArray;
import bisq.common.proto.Proto;
import bisq.common.proto.UnresolvableProtobufMessageException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;
import java.util.Set;

@ToString
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class Channel<T extends ChatMessage> implements Proto {
    private final ChannelDomain channelDomain;
    protected final String channelName;
    @EqualsAndHashCode.Include
    private transient final String id;
    protected final Observable<ChannelNotificationType> channelNotificationType = new Observable<>();

    public Channel(ChannelDomain channelDomain, String channelName, ChannelNotificationType channelNotificationType) {
        this.channelDomain = channelDomain;
        this.channelName = channelName;
        this.id = channelDomain.name().toLowerCase() + "." + channelName;
        this.channelNotificationType.set(channelNotificationType);
    }

    public bisq.chat.protobuf.Channel.Builder getChannelBuilder() {
        return bisq.chat.protobuf.Channel.newBuilder()
                .setChannelName(channelName)
                .setChannelDomain(channelDomain.toProto())
                .setChannelNotificationType(channelNotificationType.get().toProto());
    }

    abstract public bisq.chat.protobuf.Channel toProto();

    public static Channel<? extends ChatMessage> fromProto(bisq.chat.protobuf.Channel proto) {
        switch (proto.getMessageCase()) {
            case PRIVATECHANNEL: {
                return PrivateChannel.fromProto(proto, proto.getPrivateChannel());
            }

            case PRIVATETRADECHANNEL: {
                return PrivateTradeChannel.fromProto(proto, proto.getPrivateTradeChannel());
            }
            case PUBLICTRADECHANNEL: {
                return PublicTradeChannel.fromProto(proto, proto.getPublicTradeChannel());
            }


            case PUBLICCHANNEL: {
                return PublicChannel.fromProto(proto, proto.getPublicChannel());
            }

            case MESSAGE_NOT_SET: {
                throw new UnresolvableProtobufMessageException(proto);
            }
        }
        throw new UnresolvableProtobufMessageException(proto);
    }

    abstract public ObservableArray<T> getChatMessages();

    abstract public void addChatMessage(T chatMessage);

    abstract public void removeChatMessage(T chatMessage);

    abstract public void removeChatMessages(Collection<T> removeMessages);

    abstract public Set<String> getMembers();

    abstract public String getDisplayString();
}