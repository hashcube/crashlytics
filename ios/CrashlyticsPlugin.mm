#import "CrashlyticsPlugin.h"
#import <Crashlytics/Crashlytics.h>
#import <Fabric/Fabric.h>

@implementation CrashlyticsPlugin

// The plugin must call super dealloc.
- (void) dealloc {
  [super dealloc];
}


- (void) initializeWithManifest:(NSDictionary *)manifest appDelegate:(TeaLeafAppDelegate *)appDelegate {
    [Fabric with:@[[Crashlytics class]]];
}

// The plugin must call super init.
- (id) init {
  self = [super init];
  if (!self) {
    return nil;
  }
  return self;
}

- (void) log: (NSDictionary*) eventData {
  NSString * message = [eventData valueForKey:@"msg"];
  @try {
    CLS_LOG(@"{crashlytics} Logged event '%@'", message);
  } @catch (NSException *exception) {
    NSLog(@"{crashlytics} Exception while processing: %@", exception);
  }
}


- (void) setUserData: (NSDictionary*) userData {
  NSLog(@"{crashlytics} inside setUserData function");
  @try {
    [userData enumerateKeysAndObjectsUsingBlock:^(id key, id value, BOOL* stop) {
      NSString *prop = (NSString *) key;
      NSString *currValue = (NSString *) value;

      if ([prop isEqualToString:@"uid"]) {
        [CrashlyticsKit setUserIdentifier:currValue];
      } else {
        [CrashlyticsKit setObjectValue:currValue forKey:prop];
      }
    }];
  } @catch (NSException *exception) {
    NSLog(@"{crashlytics} Exception on setting user property: %@", exception);
  }
}

@end
