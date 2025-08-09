# Chat Application - API Design & Package Structure

## 1. Package Structure

```
com.chatapp
├── ChatApplication.java
├── config/
│   ├── WebSocketConfig.java
│   ├── SecurityConfig.java
│   ├── RedisConfig.java
│   ├── DatabaseConfig.java
│   └── CloudStorageConfig.java
├── controller/
│   ├── rest/
│   │   ├── AuthController.java
│   │   ├── UserController.java
│   │   ├── ConversationController.java
│   │   ├── MessageController.java
│   │   ├── MediaController.java
│   │   └── AdminController.java
│   └── websocket/
│       ├── ChatWebSocketController.java
│       ├── PresenceController.java
│       └── NotificationController.java
├── service/
│   ├── AuthService.java
│   ├── UserService.java
│   ├── ConversationService.java
│   ├── MessageService.java
│   ├── MediaService.java
│   ├── NotificationService.java
│   ├── ModerationService.java
│   └── PresenceService.java
├── repository/
│   ├── UserRepository.java
│   ├── ConversationRepository.java
│   ├── ParticipantRepository.java
│   ├── MessageRepository.java
│   ├── ReactionRepository.java
│   ├── AttachmentRepository.java
│   ├── ReadReceiptRepository.java
│   └── MembershipChangeLogRepository.java
├── entity/
│   ├── User.java
│   ├── Conversation.java
│   ├── Participant.java
│   ├── Message.java
│   ├── Reaction.java
│   ├── Attachment.java
│   ├── ReadReceipt.java
│   └── MembershipChangeLog.java
├── dto/
│   ├── request/
│   │   ├── AuthRequest.java
│   │   ├── MessageRequest.java
│   │   ├── ConversationRequest.java
│   │   └── UserUpdateRequest.java
│   ├── response/
│   │   ├── AuthResponse.java
│   │   ├── MessageResponse.java
│   │   ├── ConversationResponse.java
│   │   ├── UserResponse.java
│   │   └── PaginatedResponse.java
│   └── websocket/
│       ├── ChatMessage.java
│       ├── TypingIndicator.java
│       ├── PresenceUpdate.java
│       └── NotificationMessage.java
├── enums/
│   ├── ConversationType.java
│   ├── MessageType.java
│   ├── ParticipantRole.java
│   ├── UserStatus.java
│   └── NotificationType.java
├── exception/
│   ├── GlobalExceptionHandler.java
│   ├── ChatException.java
│   ├── UnauthorizedException.java
│   └── ValidationException.java
├── security/
│   ├── JwtUtil.java
│   ├── JwtAuthenticationFilter.java
│   ├── CustomUserDetailsService.java
│   └── SecurityContextHelper.java
├── util/
│   ├── DateUtil.java
│   ├── ValidationUtil.java
│   ├── EncryptionUtil.java
│   └── FileUtil.java
└── aspect/
    ├── RateLimitingAspect.java
    ├── LoggingAspect.java
    └── AuditAspect.java
```

## 2. REST API Endpoints

### Authentication & User Management

```http
POST   /api/v1/auth/register           # Register new user
POST   /api/v1/auth/login              # Login user
POST   /api/v1/auth/refresh            # Refresh JWT token
POST   /api/v1/auth/logout             # Logout user
POST   /api/v1/auth/forgot-password    # Request password reset
POST   /api/v1/auth/reset-password     # Reset password

GET    /api/v1/users/profile           # Get current user profile
PUT    /api/v1/users/profile           # Update user profile
GET    /api/v1/users/search            # Search users
POST   /api/v1/users/{userId}/block    # Block user
DELETE /api/v1/users/{userId}/block    # Unblock user
POST   /api/v1/users/{userId}/report   # Report user
```

### Conversations Management

```http
GET    /api/v1/conversations                    # Get user's conversations
POST   /api/v1/conversations                    # Create new conversation
GET    /api/v1/conversations/{conversationId}   # Get conversation details
PUT    /api/v1/conversations/{conversationId}   # Update conversation
DELETE /api/v1/conversations/{conversationId}   # Delete/leave conversation

# Participants Management
GET    /api/v1/conversations/{conversationId}/participants     # Get participants
POST   /api/v1/conversations/{conversationId}/participants     # Add participants
DELETE /api/v1/conversations/{conversationId}/participants/{userId}  # Remove participant
PUT    /api/v1/conversations/{conversationId}/participants/{userId}/role  # Update role

# Group/Channel specific
POST   /api/v1/conversations/{conversationId}/join            # Join via invite link
POST   /api/v1/conversations/{conversationId}/leave           # Leave group/channel
POST   /api/v1/conversations/{conversationId}/invite-link     # Generate invite link
```

### Messages

```http
GET    /api/v1/conversations/{conversationId}/messages        # Get messages (paginated)
POST   /api/v1/conversations/{conversationId}/messages        # Send message
PUT    /api/v1/messages/{messageId}                          # Edit message
DELETE /api/v1/messages/{messageId}                          # Delete message
POST   /api/v1/messages/{messageId}/reactions                # Add reaction
DELETE /api/v1/messages/{messageId}/reactions/{emoji}        # Remove reaction
POST   /api/v1/messages/{messageId}/read                     # Mark as read
GET    /api/v1/messages/search                               # Search messages
```

### Media & Attachments

```http
POST   /api/v1/media/upload            # Upload file/media
GET    /api/v1/media/{fileId}          # Download file
DELETE /api/v1/media/{fileId}          # Delete file
GET    /api/v1/media/{fileId}/preview  # Get file preview/thumbnail
```

### Admin & Moderation

```http
GET    /api/v1/admin/conversations                    # Get all conversations (admin)
POST   /api/v1/admin/conversations/{conversationId}/moderate  # Moderate conversation
GET    /api/v1/admin/users                            # Get all users (admin)
POST   /api/v1/admin/users/{userId}/suspend           # Suspend user
POST   /api/v1/admin/users/{userId}/unsuspend         # Unsuspend user
GET    /api/v1/admin/reports                          # Get user reports
PUT    /api/v1/admin/reports/{reportId}               # Handle report
```

## 3. WebSocket Events

### Connection & Presence

```javascript
// Client to Server
{
  "type": "CONNECT",
  "payload": {
    "token": "jwt-token"
  }
}

{
  "type": "SET_PRESENCE",
  "payload": {
    "status": "ONLINE|AWAY|BUSY|OFFLINE"
  }
}

// Server to Client
{
  "type": "PRESENCE_UPDATE",
  "payload": {
    "userId": 123,
    "status": "ONLINE",
    "lastSeen": "2023-12-01T10:00:00Z"
  }
}
```

### Real-time Messaging

```javascript
// Send Message
{
  "type": "SEND_MESSAGE",
  "payload": {
    "conversationId": 456,
    "content": "Hello world!",
    "messageType": "TEXT",
    "replyToId": null
  }
}

// Receive Message
{
  "type": "NEW_MESSAGE",
  "payload": {
    "id": 789,
    "conversationId": 456,
    "senderId": 123,
    "content": "Hello world!",
    "messageType": "TEXT",
    "timestamp": "2023-12-01T10:00:00Z",
    "sender": {
      "id": 123,
      "username": "john_doe",
      "avatar": "url"
    }
  }
}

// Typing Indicator
{
  "type": "TYPING_START",
  "payload": {
    "conversationId": 456
  }
}

{
  "type": "TYPING_STOP",
  "payload": {
    "conversationId": 456
  }
}

// Message Status Updates
{
  "type": "MESSAGE_READ",
  "payload": {
    "messageId": 789,
    "userId": 123,
    "readAt": "2023-12-01T10:05:00Z"
  }
}
```

## 4. Key Entity Definitions

### User Entity
```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String passwordHash;
    
    private String firstName;
    private String lastName;
    private String avatar;
    private String bio;
    
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.OFFLINE;
    
    private LocalDateTime lastSeen;
    private Boolean isActive = true;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

### Conversation Entity
```java
@Entity
@Table(name = "conversations")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConversationType type;
    
    private String name;
    private String description;
    private String avatar;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;
    
    private String inviteLink;
    private Boolean isPublic = false;
    private Boolean requiresApproval = false;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastMessageAt;
}
```

### Message Entity
```java
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;
    
    @Column(columnDefinition = "TEXT")
    private String content;
    
    @Enumerated(EnumType.STRING)
    private MessageType messageType = MessageType.TEXT;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_to_id")
    private Message replyTo;
    
    @Column(columnDefinition = "JSON")
    private String metadata; // For storing additional data like link previews
    
    private Boolean isDeleted = false;
    private Boolean isEdited = false;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

## 5. Service Layer Architecture

### MessageService Example
```java
@Service
@Transactional
public class MessageService {
    
    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private ConversationService conversationService;
    
    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @RateLimited(rate = "50/minute")
    public MessageResponse sendMessage(Long conversationId, MessageRequest request, Long senderId) {
        // Validate permissions
        if (!conversationService.canUserSendMessage(conversationId, senderId)) {
            throw new UnauthorizedException("Cannot send message to this conversation");
        }
        
        // Create message
        Message message = new Message();
        message.setContent(request.getContent());
        message.setMessageType(request.getMessageType());
        message.setConversation(conversationService.findById(conversationId));
        message.setSender(userService.findById(senderId));
        message.setCreatedAt(LocalDateTime.now());
        
        Message savedMessage = messageRepository.save(message);
        
        // Send real-time notification
        MessageResponse response = mapToResponse(savedMessage);
        messagingTemplate.convertAndSend(
            "/topic/conversation/" + conversationId,
            new ChatMessage("NEW_MESSAGE", response)
        );
        
        // Send push notifications
        notificationService.sendMessageNotification(savedMessage);
        
        return response;
    }
    
    @Cacheable(value = "messages", key = "#conversationId + '_' + #pageable.pageNumber")
    public Page<MessageResponse> getMessages(Long conversationId, Pageable pageable, Long userId) {
        if (!conversationService.isUserParticipant(conversationId, userId)) {
            throw new UnauthorizedException("Access denied");
        }
        
        Page<Message> messages = messageRepository.findByConversationIdAndIsDeletedFalse(
            conversationId, pageable
        );
        
        return messages.map(this::mapToResponse);
    }
}
```

## 6. WebSocket Configuration

```java
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");
    }
    
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
    
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new AuthenticationInterceptor());
    }
}
```

## 7. Rate Limiting & Security

```java
@Aspect
@Component
public class RateLimitingAspect {
    
    private final RedisTemplate<String, String> redisTemplate;
    
    @Around("@annotation(rateLimited)")
    public Object rateLimit(ProceedingJoinPoint joinPoint, RateLimited rateLimited) throws Throwable {
        String key = generateKey(joinPoint);
        String rate = rateLimited.rate(); // e.g., "50/minute"
        
        if (isRateLimited(key, rate)) {
            throw new TooManyRequestsException("Rate limit exceeded");
        }
        
        return joinPoint.proceed();
    }
}
```

## 8. Event-Driven Architecture

```java
@Component
public class MessageEventHandler {
    
    @EventListener
    @Async
    public void handleMessageSent(MessageSentEvent event) {
        // Update conversation last message timestamp
        conversationService.updateLastMessageTime(event.getConversationId());
        
        // Send push notifications
        notificationService.sendToParticipants(event.getMessage());
        
        // Update read receipts
        readReceiptService.markAsUnread(event.getMessage());
    }
    
    @EventListener
    public void handleUserJoinedConversation(UserJoinedEvent event) {
        // Log membership change
        auditService.logMembershipChange(event);
        
        // Notify other participants
        notificationService.notifyUserJoined(event);
    }
}
```

## 9. Caching Strategy

```yaml
# Redis Configuration
spring:
  redis:
    host: localhost
    port: 6379
  cache:
    type: redis
    redis:
      time-to-live: 600000 # 10 minutes

# Cache Keys:
# - messages:{conversationId}:{page} (TTL: 5 minutes)
# - user:{userId} (TTL: 30 minutes)
# - conversation:{conversationId} (TTL: 15 minutes)
# - presence:{userId} (TTL: 1 minute)
```

## 10. Database Considerations

```sql
-- Key Indexes for Performance
CREATE INDEX idx_messages_conversation_created_at ON messages(conversation_id, created_at DESC);
CREATE INDEX idx_messages_sender_created_at ON messages(sender_id, created_at DESC);
CREATE INDEX idx_participants_user_conversation ON participants(user_id, conversation_id);
CREATE INDEX idx_read_receipts_message_user ON read_receipts(message_id, user_id);

-- Partitioning for Large Scale (PostgreSQL)
CREATE TABLE messages_y2024m01 PARTITION OF messages
FOR VALUES FROM ('2024-01-01') TO ('2024-02-01');
```

This architecture provides a solid foundation for your chat application with proper separation of concerns, security, scalability, and real-time capabilities.
